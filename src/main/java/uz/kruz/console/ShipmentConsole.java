package uz.kruz.console;

import uz.kruz.domain.Shipment;
import uz.kruz.dto.ShipmentDTO;
import uz.kruz.repository.impl.OrderRepositoryImpl;
import uz.kruz.repository.impl.ShipmentRepositoryImpl;
import uz.kruz.service.ShipmentService;
import uz.kruz.service.impl.ShipmentServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.sql.Date;
import java.util.Optional;

public class ShipmentConsole {
    private final ShipmentService shipmentService;
    private final ConsoleUtil consoleUtil;
    private final Narrator narrator;

    public ShipmentConsole() {
        this.shipmentService = new ShipmentServiceImpl(new ShipmentRepositoryImpl(), new OrderRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }
    public void showAll(){
        try {
            narrator.sayln("\n\t > All Orders: ");
            for (Shipment shipment : shipmentService.findAll()) {
                narrator.sayln("\t > " + shipment.toString());
            }
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void register() {
        while (true) {
            Integer orderId = consoleUtil.getValueOfInteger("\n(0. Shipment Menu)\n Enter Order ID: ");
            if (orderId == 0) {
                return;
            }
            String trackingNum = consoleUtil.getValueOf("\nEnter Tracking Number: ");
            if (trackingNum.equals("0")){
                return;
            }
            try {
                Validator.validateInteger(orderId,  "Order ID");
                Validator.validateString(trackingNum,  "Tracking Number");
                ShipmentDTO shipmentDTO = ShipmentDTO.builder()
                        .orderId(orderId)
                        .trackingNo(trackingNum)
                        .build();
                shipmentService.register(shipmentDTO);
                narrator.sayln("\n\t > Shipment Registered!" +  shipmentDTO.toString());
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }

        }
    }
}
