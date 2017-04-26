package ru.mti.bankclient.server;

import java.util.Timer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.session.AccountFacade;
import ru.mti.bankclient.session.ClientFacade;
import ru.mti.bankclient.session.DepositFacade;
import ru.mti.bankclient.session.OperationFacade;
import ru.mti.bankclient.session.PartnerBankFacade;
import ru.mti.bankclient.session.ProviderCategoryFacade;
import ru.mti.bankclient.session.ServiceProviderFacade;
import ru.mti.bankclient.session.TemplateFacade;
import ru.mti.bankclient.shared.Account;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.BankMessage;
import ru.mti.bankclient.shared.BankMessageDTO;
import ru.mti.bankclient.shared.Client;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.Deposit;
import ru.mti.bankclient.shared.DepositDTO;
import ru.mti.bankclient.shared.OperTypes;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.OperationType;
import ru.mti.bankclient.shared.PartnerBank;
import ru.mti.bankclient.shared.PartnerBankDTO;
import ru.mti.bankclient.shared.ProviderCategory;
import ru.mti.bankclient.shared.ServiceProvider;
import ru.mti.bankclient.shared.ServiceProviderDTO;
import ru.mti.bankclient.shared.Status;
import ru.mti.bankclient.shared.Statuses;
import ru.mti.bankclient.shared.Template;
import ru.mti.bankclient.shared.TemplateDTO;

/**
 *
 * @author Белых Евгений
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    private static final long serialVersionUID = 4456105400553118785L;

    @EJB
    private ClientFacade clientFacade;
    @EJB
    private OperationFacade operationFacade;
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private DepositFacade depositFacade;
    @EJB
    private ProviderCategoryFacade providerCategoryFacade;
    @EJB
    private ServiceProviderFacade serviceProviderFacade;
    @EJB
    private PartnerBankFacade partnerBankFacade;
    @EJB
    private TemplateFacade templateFacade;

    private static final int CURRENT_BANK = 1;

    private static final int IN_TRANSFER = 1;
    private static final int OUT_TRANSFER = 2;
    private static final int SERVICE_PAY = 3;
    private static final int CARD_BLOCK = 4;
    private static final int VIRTUAL_CARD = 5;

    private int tryCount = 5;

    @Override
    public ClientDTO loginServer(String login, String pass) {
        Client client;
        ClientDTO user = null;
        int tryCount;

        if (getTryCountInSesstion() != null) {
            tryCount = getTryCountInSesstion();
        } else {
            tryCount = this.tryCount;
            setTryCountInSesstion(tryCount);
        }

        //получаем объект клиента из базы данных
        client = clientFacade.findByLogin(login);

        if (client != null) {

            user = createClientDTO(client);

            //отправляем сведения о блокировке
            if (client.getBlocked()) {
                return user;
            }

            //уменьшаем количество попыток входа
            tryCount--;
            setTryCountInSesstion(tryCount);

            //блокируем пользователя
            if (tryCount < 1) {
                client.setBlocked(true);
                clientFacade.edit(client);
                user.setBlocked(true);
                return user;
            }
            //если неверный пароль, то возвращаем количество попыток
            if (!client.getPassword().equals(pass)) {
                user.setPassword(String.valueOf(tryCount));
            }
        }

        // сохраняем объект клиента в сессии
        storeUserInSession(user);

        return user;
    }

    /**
     * Создает DTO для сущности Client
     *
     * @param client - объект клиента
     * @return DTO
     */
    private ClientDTO createClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setLogin(client.getLogin());
        clientDTO.setPassword(client.getPassword());
        clientDTO.setBlocked(client.getBlocked());
        clientDTO.setAdmin(client.getAdmin());

        if (client.getAccountList() != null) {
            for (Account acc : client.getAccountList()) {
                clientDTO.getAccountList().add(createAccountDTO(acc));
            }
        }

        if (client.getBankMessageList() != null) {
            for (BankMessage bankMessage : client.getBankMessageList()) {
                clientDTO.getBankMessageList().add(createBankMessageDTO(bankMessage));
            }
        }

        return clientDTO;
    }

    @Override
    public ClientDTO loginFromSessionServer() {
        return getUserAlreadyFromSession();
    }

    @Override
    public void logout() {
        deleteUserFromSession();
    }

    /**
     * выбирает объект клиента из сессии
     *
     * @return DTO клиента
     */
    private ClientDTO getUserAlreadyFromSession() {
        ClientDTO user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof ClientDTO) {
            user = (ClientDTO) userObj;
        }

        return user;
    }

    /**
     * сохраняет объект клиента в сессии
     *
     * @param user DTO клиента
     */
    private void storeUserInSession(ClientDTO user) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }

    /**
     * сохраняет количество попыток входа в атрибутах сессии
     *
     * @param count - количество попыток входа
     */
    private void setTryCountInSesstion(int count) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("tryCount", count);
    }

    /**
     * выбирает количество поппыток входа из сессии
     *
     * @return - оставшееся количество попыток
     */
    private Integer getTryCountInSesstion() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        Integer count = (Integer) session.getAttribute("tryCount");
        return count;
    }

    /**
     * удаляет клиента из сессии и разрывает соединение
     */
    private void deleteUserFromSession() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        session.removeAttribute("tryCount");
        session.invalidate();
    }

    /**
     * Создает DTO для сущности Account
     *
     * @param account - объект счета клиента
     * @return DTO
     */
    private AccountDTO createAccountDTO(Account account) {

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(account.getId());
        accountDTO.setNumber(account.getNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setOverlimit(account.getOverlimit());
        accountDTO.setInterestRate(account.getInterestRate());
        accountDTO.setLoanRate(account.getLoanRate());
        accountDTO.setBlocked(account.getBlocked());
        accountDTO.setClientId(account.getClientId().getId());
        accountDTO.setAccountTypeId(account.getAccountTypeId().getId());
        accountDTO.setAccountTypeName(account.getAccountTypeId().getName());
        accountDTO.setCurrencyId(account.getCurrencyId().getId());
        accountDTO.setCurrencyName(account.getCurrencyId().getName());
        accountDTO.setCurrencyCode(account.getCurrencyId().getCode());
        accountDTO.setCardNumber(account.getCardNumber());
        accountDTO.setExpirationDate(account.getExpirationDate());
        accountDTO.setCvv(account.getCvv());

        if (account.getTemplateList() != null) {
            for (Template template : account.getTemplateList()) {
                accountDTO.getTemplateList().add(createTemplateDTO(template));
            }
        }

        if (account.getOperationList() != null) {
            for (Operation oper : account.getOperationList()) {
                accountDTO.getOperationList().add(createOperationDTO(oper));
            }
        }

        return accountDTO;
    }

    /**
     * Выбирает счета клиента
     *
     * @param clientId - код клиента
     * @return - список счетов клиента
     */
    @Override
    public List<AccountDTO> getAccounts(int clientId) {

        Client client = clientFacade.find(clientId);
        ClientDTO clientDTO = createClientDTO(client);
        List list = new ArrayList(clientDTO.getAccountList());
        return list;

    }

    /**
     * Создает DTO для сущности PartnerDTO
     *
     * @param partnerBank - объект банка-корреспондента
     * @return DTO
     */
    private PartnerBankDTO createPartnerBankDTO(PartnerBank partnerBank) {

        PartnerBankDTO partnerBankDTO = new PartnerBankDTO(partnerBank.getId(),
                partnerBank.getName(), partnerBank.getInn(), partnerBank.getKpp(),
                partnerBank.getBik(), partnerBank.getCorrAccount());

        return partnerBankDTO;
    }

    /**
     * Создает DTO для сущности PartnerDTO
     *
     * @param operation - объект операции
     * @return DTO
     */
    private OperationDTO createOperationDTO(Operation operation) {

        OperationDTO operationDTO = new OperationDTO(operation.getId(),
                operation.getCreateDate(), operation.getDescription(),
                operation.getDestinationAccount(), operation.getNumber(),
                operation.getExecutionDate(), operation.getAmount(),
                operation.getComment(), operation.getAccountId().getId(),
                operation.getOperationTypeId().getId(), operation.getOperationTypeId().getName(),
                createPartnerBankDTO(operation.getPartnerBankId()),
                operation.getStatusId().getId(), operation.getStatusId().getName());

        return operationDTO;
    }

    /**
     * Сохраняет операцию в базу данных
     *
     * @param operationDTO - DTO операции
     */
    @Override
    public void saveOperation(OperationDTO operationDTO) {

        operationFacade.create(new Operation(operationDTO));

        // получаем объект пользователя из сессии
        ClientDTO user = getUserAlreadyFromSession();

    }

    @Override
    public void executeOperation(OperationDTO oper) {

        // получаем объект пользователя из сессии
        ClientDTO user = getUserAlreadyFromSession();

        OperationDTO returnedOper = null;

        switch (oper.getOperationTypeId()) {
            case CARD_BLOCK:
                returnedOper = cardBlock(oper);
                break;
            case IN_TRANSFER:
                returnedOper = executeInTransfer(oper);
                break;
            case OUT_TRANSFER:
                returnedOper = executeOutTransfer(oper);
                break;
            case VIRTUAL_CARD:
                break;
            case SERVICE_PAY:
                returnedOper = executeServicePay(oper);
                break;
        }

        // ставим операцию в список операций по счету
        for (AccountDTO acc : user.getAccountList()) {
            if (acc.getId() == oper.getAccountId()) {
                acc.getOperationList().add(returnedOper);
            }
        }

        // обновляем объект клиента в сессии
        storeUserInSession(user);
    }

    /**
     * Исполняет операцию без проверок
     *
     * @param oper - объект DTO операции
     */
    private OperationDTO simpleExecuteOperation(OperationDTO oper, String comment, Statuses status) {

        oper.setComment(comment);
        oper.setExecutionDate(new Date(System.currentTimeMillis()));
        oper.setNumber(1 + (int) (Math.random() * ((100000 - 1) + 1)));
        oper.setStatusId(status.getId());
        oper.setStatusName(status.getName());

        operationFacade.edit(new Operation(oper));
        return oper;
    }

    /**
     * Блокирует карту клиента
     *
     * @param oper - объект DTO операции
     */
    private OperationDTO cardBlock(OperationDTO oper) {
        // находим нужный счет и ставим флаг блокировки
        Account blockedCard = accountFacade.find(oper.getAccountId());
        blockedCard.setBlocked(true);
        accountFacade.edit(blockedCard);
        // исполняем операцию
        return simpleExecuteOperation(oper, "Карта успешно заблокирована", Statuses.EXECUTED);
    }

    /**
     * Исполняет перевод внутри банка
     *
     * @param oper - объект DTO операции
     */
    private OperationDTO executeInTransfer(OperationDTO oper) {

        Statuses status = Statuses.NOT_EXECUTED;
        String comment = "";

        // получаем объект пользователя из сессии
        ClientDTO user = getUserAlreadyFromSession();

        Account destinationAccount = accountFacade.findByNumber(oper.getDestinationAccount());

        if (destinationAccount != null) {
            // выбираем счет списания
            Account transferAccount = accountFacade.find(oper.getAccountId());
            // уменьшаем остаток на счете
            transferAccount.setBalance(transferAccount.getBalance() - oper.getAmount());
            // сохраняем значение в базе
            accountFacade.edit(transferAccount);
            // увеличиваем остаток на счете назначения
            destinationAccount.setBalance(destinationAccount.getBalance() + oper.getAmount());
            // сохраняем значение в базе
            accountFacade.edit(destinationAccount);

            // если перевод другому клиенту банка, то делаем приходную операцию у получателя
            if (destinationAccount.getClientId().getId() != transferAccount.getClientId().getId()) {
                // создаем объект операции
                Operation operation = new Operation();
                operation.setAccountId(destinationAccount);
                operation.setAmount(oper.getAmount());
                operation.setCreateDate(new Date(System.currentTimeMillis()));
                operation.setDescription("Перевод от " + user.getName());
                operation.setDestinationAccount(transferAccount.getNumber());
                operation.setOperationTypeId(new OperationType(OperTypes.TRANSFER_IN.getId()));
                operation.setStatusId(new Status(Statuses.EXECUTED.getId()));
                operation.setPartnerBankId(new PartnerBank(CURRENT_BANK));
                operation.setComment("Перевод завершен успешно");
                operation.setExecutionDate(new Date(System.currentTimeMillis()));
                operation.setNumber(1 + (int) (Math.random() * ((100000 - 1) + 1)));
                operationFacade.create(operation);
            }

            for (AccountDTO acc : user.getAccountList()) {
                if (acc.getId() == transferAccount.getId()) {
                    acc.setBalance(transferAccount.getBalance());
                }
                if (acc.getId() == destinationAccount.getId()) {
                    acc.setBalance(destinationAccount.getBalance());
                }
            }

            // устанавливаем статус исполнено
            status = Statuses.EXECUTED;
            // определяем комментарий
            comment = "Перевод завершен успешно";
        } else {
            // устанавливаем статус не исполнено
            status = Statuses.NOT_EXECUTED;
            // определяем комментарий
            comment = "Неверно указан счет получателя";
        }

        // исполняем операцию
        return simpleExecuteOperation(oper, comment, status);
    }

    /**
     * Исполняет перевод из банка в другой банк
     *
     * @param oper - объект DTO операции
     */
    private OperationDTO executeOutTransfer(OperationDTO oper) {

        // выбираем счет списания
        Account transferAccount = accountFacade.find(oper.getAccountId());
        // уменьшаем остаток на счете
        transferAccount.setBalance(transferAccount.getBalance() - oper.getAmount());
        // сохраняем значение в базе
        accountFacade.edit(transferAccount);

        // получаем объект пользователя из сессии
        ClientDTO user = getUserAlreadyFromSession();

        for (AccountDTO acc : user.getAccountList()) {
            if (acc.getId() == transferAccount.getId()) {
                acc.setBalance(transferAccount.getBalance());
            }
        }

        // устанавливаем статус исполнено
        Statuses status = Statuses.EXECUTED;
        // определяем комментарий
        String comment = "Перевод завершен успешно";

        // исполняем операцию
        return simpleExecuteOperation(oper, comment, status);
    }

    
        /**
     * Исполняет перевод из банка в другой банк
     *
     * @param oper - объект DTO операции
     */
    private OperationDTO executeServicePay(OperationDTO oper) {

        // выбираем счет списания
        Account transferAccount = accountFacade.find(oper.getAccountId());
        // уменьшаем остаток на счете
        transferAccount.setBalance(transferAccount.getBalance() - oper.getAmount());
        // сохраняем значение в базе
        accountFacade.edit(transferAccount);

        // получаем объект пользователя из сессии
        ClientDTO user = getUserAlreadyFromSession();

        for (AccountDTO acc : user.getAccountList()) {
            if (acc.getId() == transferAccount.getId()) {
                acc.setBalance(transferAccount.getBalance());
            }
        }

        // устанавливаем статус исполнено
        Statuses status = Statuses.EXECUTED;
        // определяем комментарий
        String comment = "Платеж завершен успешно";

        // исполняем операцию
        return simpleExecuteOperation(oper, comment, status);
    }
    
    
    /**
     * Создает DTO для сущности Deposit - депозит
     *
     * @param deposit - объект депозит
     * @return DTO
     */
    private DepositDTO createDepositDTO(Deposit deposit) {

        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setId(deposit.getId());
        depositDTO.setName(deposit.getName());
        depositDTO.setDuration(deposit.getDuration());
        depositDTO.setInterestRate(deposit.getInterestRate());
        depositDTO.setDiscription(deposit.getDiscription());

        return depositDTO;
    }

    /**
     * Выбирает список депозитов
     *
     * @return список DTO депозитов
     */
    @Override
    public List<DepositDTO> getDeposits() {

        List<DepositDTO> depositDTOList = new ArrayList();
        List<Deposit> depositList = depositFacade.findAll();
        for (Deposit deposit : depositList) {
            DepositDTO depositDTO = createDepositDTO(deposit);
            depositDTOList.add(depositDTO);
        }
        return depositDTOList;
    }

    /**
     * Создает DTO для сущности ServiceProvider - поставщик услуг
     *
     * @param serviceProvider - объект поставщика услуг
     * @return DTO
     */
    private ServiceProviderDTO createServiceProviderDTO(ServiceProvider serviceProvider) {

        ServiceProviderDTO serviceProviderDTO = new ServiceProviderDTO();
        serviceProviderDTO.setAccountNumber(serviceProvider.getAccountNumber());
        serviceProviderDTO.setId(serviceProvider.getId());
        serviceProviderDTO.setInn(serviceProvider.getInn());
        serviceProviderDTO.setName(serviceProvider.getName());
        serviceProviderDTO.setPartnerBankId(serviceProvider.getPartnerBankId().getId());

        return serviceProviderDTO;
    }

    /**
     * Создает DTO для сущности Template - шаблон операции
     *
     * @param template - объект шаблона
     * @return DTO
     */
    private TemplateDTO createTemplateDTO(Template template) {

        TemplateDTO templateDTO = new TemplateDTO();
        templateDTO.setAccountId(template.getAccountId().getId());
        templateDTO.setDescription(template.getDescription());
        templateDTO.setDestinationAccount(template.getDestinationAccount());
        templateDTO.setId(template.getId());
        templateDTO.setName(template.getName());
        templateDTO.setOperationTypeId(template.getOperationTypeId().getId());
        templateDTO.setPartnerBankId(template.getPartnerBankId().getId());
        templateDTO.setAmount(template.getAmount());

        return templateDTO;
    }

    /**
     * Создает DTO для сущности BankMessage - сообщение от банка
     *
     * @param bankMessage - объект сообщения банка
     * @return DTO
     */
    private BankMessageDTO createBankMessageDTO(BankMessage bankMessage) {

        BankMessageDTO bankMessageDTO = new BankMessageDTO();
        bankMessageDTO.setClientId(bankMessage.getClientId().getId());
        bankMessageDTO.setId(bankMessage.getId());
        bankMessageDTO.setMessageDate(bankMessage.getMessageDate());
        bankMessageDTO.setText(bankMessage.getText());

        return bankMessageDTO;
    }

    /**
     * Выбирает всех поставщиков услуг указанной категории
     *
     * @param categories - перечисление категорий
     * @return - список DTO сущностей поставщиков услуг
     *
     */
    @Override
    public List<ServiceProviderDTO> getServiceProviderByCategory(int categorieId) {

        List<ServiceProviderDTO> serviceProviderDTOList = new ArrayList();
        ProviderCategory providerCategory = providerCategoryFacade.find(categorieId);
        List<ServiceProvider> serviceProviderList = providerCategory.getServiceProviderList();

        for (ServiceProvider provider : serviceProviderList) {
            serviceProviderDTOList.add(createServiceProviderDTO(provider));
        }

        return serviceProviderDTOList;
    }

    @Override
    public void openDeposit(DepositDTO depositDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Находит поставщика услуг по ИНН
     *
     * @param inn - ИНН
     * @return DTO поставщика услуг
     */
    @Override
    public ServiceProviderDTO getServiceProviderByInn(String inn) {

        ServiceProvider provider = serviceProviderFacade.findByInn(inn);

        return createServiceProviderDTO(provider);

    }

    /**
     * Находит банк-контрагент по БИК
     *
     * @param inn - БИК
     * @return DTO банка-контрагента
     */
    @Override
    public PartnerBankDTO getPartnerBankByBik(String bik) {

        PartnerBank bank = partnerBankFacade.findByBik(bik);

        return createPartnerBankDTO(bank);
    }

    /**
     * сохраняет в базе объект шаблон
     *
     * @param templateDTO - DTO шаблона
     */
    @Override
    public void saveTemplate(TemplateDTO templateDTO) {

        templateFacade.create(new Template(templateDTO));
    }

}
