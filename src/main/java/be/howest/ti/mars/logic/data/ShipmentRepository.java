package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.classes.Colony;
import be.howest.ti.mars.logic.classes.Resource;
import be.howest.ti.mars.logic.classes.Shipment;
import be.howest.ti.mars.logic.classes.Status;
import be.howest.ti.mars.logic.exceptions.CorruptedDataException;
import be.howest.ti.mars.logic.exceptions.H2RuntimeException;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.howest.ti.mars.logic.data.H2Statements.H2_GET_TRANSPORT_DETAILS;
import static be.howest.ti.mars.logic.data.H2Statements.H2_GET_TRANSPORT_RESOURCES;

public class ShipmentRepository {
    public static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());
    private static final ShipmentRepository INSTANCE = new ShipmentRepository();

    private ShipmentRepository(){}

    public static ShipmentRepository getInstance() {
        return INSTANCE;
    }

    public Set<Shipment> getShipments(int companyId) {
        Set<Shipment> res = new HashSet<>();
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_TRANSPORT_DETAILS)) {
            stmt.setInt(1, companyId);
            stmt.setInt(2, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    res.add(covertToShipment(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, MarsRepository.GENERIC_SQL_ERROR);
            throw new H2RuntimeException(ex.getMessage());
        }
        if (res.isEmpty()) {
            LOGGER.log(Level.INFO, "Potential empty company detected; Running Existence check.");
            CompanyRepository.getInstance().existenceCheck(companyId);
        }
        return res;
    }

    private Status getStatus(ResultSet rs, int id) throws SQLException {
        switch (rs.getString("STATUS")) {
            case "In Transit":
                return Status.IN_TRANSPORT;
            case "Payed":
                return Status.PAYED;
            case "Processing":
                return Status.PROCESSING;
            case "Delivered":
                return Status.DELIVERED;
            default:
                LOGGER.log(Level.SEVERE, () ->
                        String.format("Corrupted data discovered: Status column in row with id %s at table SHIPMENTS in schema MARSDEX",
                                id));
                throw new CorruptedDataException("Status enum not recognized");
        }
    }

    private Set<Resource> getShipmentResources(int shipmentId) throws SQLException {
        try (Connection con = MarsRepository.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(H2_GET_TRANSPORT_RESOURCES)) {
            stmt.setInt(1, shipmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                Set<Resource> resources = new HashSet<>();
                while (rs.next()) {
                    resources.add(MarsRepository.convertToResource(rs));
                }
                return resources;
            }
        }
    }

    private Shipment covertToShipment(ResultSet rs) throws SQLException {
        try {
            int id = rs.getInt("ID");
            Colony sender = ColonyRepository.getInstance().getColony(rs.getInt("SENDER_ID"));
            Colony receiver = ColonyRepository.getInstance().getColony(rs.getInt("RECEIVER_ID"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendDate = new Timestamp(df.parse(rs.getString("SEND_TIME")).getTime()).toLocalDateTime();
            Status status = getStatus(rs, id);
            rs.getObject("RECEIVE_TIME");
            if (!rs.wasNull()) {
                LocalDateTime receiveDate = new Timestamp(df.parse(rs.getString("RECEIVE_TIME")).getTime()).toLocalDateTime();
                return new Shipment(id, sender, sendDate, receiver, receiveDate, getShipmentResources(id), status);
            } else {
                return new Shipment(id, sender, sendDate, receiver, getShipmentResources(id), status);
            }
        } catch (ParseException ex) {
            String msg = String.format("Date corrupted at row %s in table SHIPMENTS in Scheme MARSDEX", rs.getInt("ID"));
            LOGGER.log(Level.SEVERE, msg);
            throw new CorruptedDataException("Date data corrupted");
        }
    }
}
