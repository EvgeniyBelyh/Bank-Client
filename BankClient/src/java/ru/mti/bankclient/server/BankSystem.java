
package ru.mti.bankclient.server;

import java.util.Date;
import java.util.List;
import ru.mti.bankclient.session.OperationFacade;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.Status;
import ru.mti.bankclient.shared.Status;
import ru.mti.bankclient.shared.Statuses;
import ru.mti.bankclient.shared.Statuses;

/**
 * Класс эмулирует поведение АБС
 * @author Евгений Белых
 */
public class BankSystem {
    
    OperationFacade operationFacade;
    
    /**
     * Исполняет операции в статусе NEW(1)
     */
    public void executeOperations() {
        // выбираем все новые операции из базы данных
        List<Operation> operationList = operationFacade.findByStatus(Statuses.NEW.getId());
        
        for(Operation operation : operationList) {
            operation.setNumber(getOperationNumber());
            operation.setExecutionDate(new Date(System.currentTimeMillis()));
            operation.setStatusId(new Status(Statuses.EXECUTED.getId()));
            operationFacade.edit(operation);
        }
    }
    
    /**
     * Генерирует случайный номер операции
     * @return номер операции
     */
    private int getOperationNumber() {
        return 1 + (int)(Math.random() * ((100000 - 1) + 1));
    }
    
}
