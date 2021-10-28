package Servicee;

import java.util.Calendar;
import java.util.Date;

import Cont.Contract;
import Cont.Installment;

public class ContractService {
    
    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, int months) {

        double basicQuota = contract.getTotalValue() / months;

        for (int i = 1; i <= months; i++) {

            double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
            //
            double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
            //
            Date dueDate = addMonths(contract.getDate(), i);
            contract.gInstallments().add(new Installment(dueDate, fullQuota));
        }
    }

    private Date addMonths(Date date, int N) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.MONTH, N);
        return calender.getTime();
    }
}
