package pets.database.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.util.StringUtils.hasText;
import static pets.database.utils.Commons.convertDtoToEntityRefAccountTypes;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import pets.database.model.RefAccountTypeDto;
import pets.database.repository.RefAccountTypeRepository;
import pets.models.model.RefAccountTypeRequest;
import pets.models.model.RefAccountTypeResponse;
import pets.models.model.Status;

@Service
public class RefAccountTypeServiceDb {
    private static final Logger logger = LoggerFactory.getLogger(RefAccountTypeServiceDb.class);

    private final RefAccountTypeRepository refAccountTypeDao;

    public RefAccountTypeServiceDb(RefAccountTypeRepository refAccountTypeDao) {
        this.refAccountTypeDao = refAccountTypeDao;
    }

    public RefAccountTypeResponse getAllRefAccountTypes() {
        logger.info("Before Get All Ref Account Types");
        List<RefAccountTypeDto> refAccountTypes = new ArrayList<>();
        Status status = null;

        try {
            refAccountTypes = refAccountTypeDao.getAllRefAccountTypes();
        } catch (Exception ex) {
            logger.error("Get All Ref Account Types");
            status = Status.builder()
                    .errMsg("Error Retrieving All Account Types, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Get All Ref Account Types: {}", refAccountTypes.size());
        return RefAccountTypeResponse.builder()
                .refAccountTypes(convertDtoToEntityRefAccountTypes(refAccountTypes))
                .status(status)
                .build();
    }

    public RefAccountTypeResponse getRefAccountTypeById(String id) {
        logger.info("Before Get Ref Account Type By Id: {}", id);
        RefAccountTypeDto refAccountType = null;
        Status status = null;

        try {
            refAccountType = refAccountTypeDao.getRefAccountTypeById(id);
        } catch (Exception ex) {
            logger.error("Get Ref Account Type By Id: {}", id, ex);
            status = Status.builder()
                    .errMsg("Error Retrieving Account Type, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Get Ref Account Type By Id: {} | {}", id, refAccountType);
        return RefAccountTypeResponse.builder()
                .refAccountTypes(refAccountType == null ? emptyList() : convertDtoToEntityRefAccountTypes(singletonList(refAccountType)))
                .status(status)
                .build();
    }

    public RefAccountTypeResponse saveNewRefAccountType(RefAccountTypeRequest refAccountTypeRequest) {
        logger.info("Before Save New Ref Account Type: {}", refAccountTypeRequest);
        RefAccountTypeDto newRefAccountType;
        Status status = null;

        try {
            newRefAccountType = RefAccountTypeDto.builder()
                    .description(refAccountTypeRequest.getDescription())
                    .creationDate(LocalDate.now().toString())
                    .lastModified(LocalDateTime.now().toString())
                    .build();

            newRefAccountType = refAccountTypeDao.saveNewRefAccountType(newRefAccountType);

            if (!hasText(newRefAccountType.getId())) {
                newRefAccountType = null;
                status = Status.builder()
                        .errMsg("Error Saving Account Type, Please Try Again!!!")
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Save New Ref Account Type: {}", refAccountTypeRequest, ex);
            newRefAccountType = null;
            status = Status.builder()
                    .errMsg("Error Saving Account Type, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Save New Ref Account Type: {}", newRefAccountType);
        return RefAccountTypeResponse.builder()
                .refAccountTypes(newRefAccountType == null ? emptyList() : convertDtoToEntityRefAccountTypes(singletonList(newRefAccountType)))
                .status(status)
                .build();
    }

    public RefAccountTypeResponse updateRefAccountTypeById(String id, RefAccountTypeRequest refAccountTypeRequest) {
        logger.info("Before Update Ref Account Type By Id: {} | {}", id, refAccountTypeRequest);
        RefAccountTypeResponse refAccountTypeResponse;
        Status status;

        try {
            Update update = new Update();
            if (hasText(refAccountTypeRequest.getDescription())) {
                update.set(FIELD_NAME_DESCRIPTION, refAccountTypeRequest.getDescription());
            }
            update.set("lastModified", LocalDateTime.now().toString());

            long modifiedCount = refAccountTypeDao.updateRefAccountTypeById(id, update);

            if (modifiedCount > 0) {
                refAccountTypeResponse = getRefAccountTypeById(id);
            } else {
                status = Status.builder()
                        .errMsg("Error Updating Account Type, Please Try Again!!!")
                        .build();
                refAccountTypeResponse = RefAccountTypeResponse.builder()
                        .refAccountTypes(emptyList())
                        .status(status)
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Update Ref Account Type By Id: {} | {}", id, refAccountTypeRequest, ex);
            status = Status.builder()
                    .errMsg("Error Updating Account Type, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
            refAccountTypeResponse = RefAccountTypeResponse.builder()
                    .refAccountTypes(emptyList())
                    .status(status)
                    .build();
        }

        logger.info("After Update Ref Account Type By Id: {} | {}", id, refAccountTypeResponse);
        return refAccountTypeResponse;
    }

    public RefAccountTypeResponse deleteRefAccountTypeById(String id) {
        logger.info("Before Delete Ref Account Type By Id: {}", id);
        long deleteCount = 0;
        Status status = null;

        try {
            deleteCount = refAccountTypeDao.deleteRefAccountTypeById(id);
        } catch (Exception ex) {
            logger.error("Delete Ref Account Type By Id: {}", id, ex);
            status = Status.builder()
                    .errMsg("Error Deleting Account Type, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Delete Ref Account Type By Id: {} | {}", id, deleteCount);
        return RefAccountTypeResponse.builder()
                .refAccountTypes(emptyList())
                .deleteCount(deleteCount)
                .status(status)
                .build();
    }
}
