package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.daos.ReimbursementStatusDAO;
import com.revature.ers.daos.ReimbursementTypeDAO;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.MethodNotAllowedException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReimbursementServiceTest {
    private ReimbursementService sut;
    private final ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);
    private final ReimbursementStatusDAO mockReimbursementStatusDao = mock(ReimbursementStatusDAO.class);
    private final ReimbursementTypeDAO mockReimbursementTypeDao = mock(ReimbursementTypeDAO.class);

    @Before
    public void setup(){
        sut = new ReimbursementService(mockReimbursementDao, mockReimbursementTypeDao, mockReimbursementStatusDao);
    }
    @Test
    public void test_updateStatus_valid(){
        // Arrange
        ReimbursementService spiedSut = Mockito.spy(sut);
        String validStatus = "DENIED";
        String validReimbID = "PENDING";
        String validResId = "01";
        when(spiedSut.isValidStatus(validStatus)).thenReturn(true);
        when(spiedSut.isValidId(validReimbID)).thenReturn(true);
        when(mockReimbursementDao.getStatusIDfromReimbID(validReimbID)).thenReturn("PENDING");

        when(spiedSut.isPending(validReimbID)).thenReturn(true);
        when(mockReimbursementStatusDao.getStatusId(validStatus)).thenReturn("filler");
        when(mockReimbursementDao.getById(validReimbID)).thenReturn(new Reimbursement());
        when(mockReimbursementStatusDao.getStatusId("PENDING")).thenReturn("PENDING");
        // Act
        sut.updateStatus(validStatus,validReimbID,validResId);
    }
    @Test (expected = ResourceConflictException.class)
    public void test_updateStatus_invalid_status(){
        // Arrange
        ReimbursementService spiedSut = Mockito.spy(sut);
        String invalidStatus = "DENIED";
        String validReimbID = "PENDING";
        String validResId = "01";
        when(spiedSut.isValidStatus(invalidStatus)).thenReturn(true);
        when(spiedSut.isValidId(validReimbID)).thenReturn(true);
        when(mockReimbursementDao.getStatusIDfromReimbID(validReimbID)).thenReturn("PENDING");

        when(spiedSut.isPending(validReimbID)).thenReturn(true);
        when(mockReimbursementDao.getById(validReimbID)).thenReturn(new Reimbursement());
        when(mockReimbursementStatusDao.getStatusId("PENDING")).thenReturn("PENDING");
        // Act
        sut.updateStatus(invalidStatus,validReimbID,validResId);
    }
    @Test (expected = ResourceConflictException.class)
    public void test_updateStatus_invalid_ReimbursementId(){
        // Arrange
        ReimbursementService spiedSut = Mockito.spy(sut);
        String validStatus = "DENIED";
        String invalidReimbID = "PENDING";
        String validResId = "01";
        when(spiedSut.isValidStatus(validStatus)).thenReturn(true);
        when(spiedSut.isValidId(invalidReimbID)).thenReturn(true);
        when(mockReimbursementDao.getStatusIDfromReimbID(invalidReimbID)).thenReturn("PENDING");

        when(spiedSut.isPending(invalidReimbID)).thenReturn(true);
        when(mockReimbursementStatusDao.getStatusId(validStatus)).thenReturn("filler");
        when(mockReimbursementStatusDao.getStatusId("PENDING")).thenReturn("PENDING");
        // Act
        sut.updateStatus(validStatus,invalidReimbID,validResId);
    }
    @Test (expected = MethodNotAllowedException.class)
    public void test_updateStatus_not_pending(){
        // Arrange
        ReimbursementService spiedSut = Mockito.spy(sut);
        String validStatus = "DENIED";
        String validReimbID = "NOTPENDING";
        String validResId = "01";
        when(spiedSut.isValidStatus(validStatus)).thenReturn(true);
        when(spiedSut.isValidId(validReimbID)).thenReturn(true);
        when(mockReimbursementDao.getStatusIDfromReimbID(validReimbID)).thenReturn("NOTPENDING");

        when(spiedSut.isPending(validReimbID)).thenReturn(true);
        when(mockReimbursementStatusDao.getStatusId(validStatus)).thenReturn("filler");
        when(mockReimbursementDao.getById(validReimbID)).thenReturn(new Reimbursement());
        when(mockReimbursementStatusDao.getStatusId("PENDING")).thenReturn("PENDING");
        // Act
        sut.updateStatus(validStatus,validReimbID,validResId);
    }

    @Test
    public void test_getAll(){
        when(mockReimbursementDao.getAll()).thenReturn(new ArrayList<Reimbursement>());
        sut.getAll();
    }
    @Test
    public void test_getByStatus(){
        String validFilter = "TEST";
        when(mockReimbursementDao.getByStatus(validFilter)).thenReturn(new ArrayList<Reimbursement>());
        sut.getByStatus(validFilter);
    }
    @Test
    public void test_getPending(){
        String validFilter = "PENDING";
        when(mockReimbursementDao.getByStatus(validFilter)).thenReturn(new ArrayList<Reimbursement>());
        sut.getPending(validFilter);
    }
    @Test
    public void test_getHistory(){
        Principal principal = new Principal("00000","username","MANAGER");
        when(mockReimbursementDao.getManagerHistory(principal.getId())).thenReturn(new ArrayList<Reimbursement>());
        sut.getHistory(principal);
    }
    @Test
    public void test_ReimbursementResponse_valid(){
        String username = "username";
        when(mockReimbursementDao.getAllResponseByUsername(username)).thenReturn(new HashMap<>());

        sut.getReimbursementResponseList(username);
    }
    @Test(expected = InvalidRequestException.class)
    public void test_ReimbursementResponse_invalid(){
        String username = "username";
        when(mockReimbursementDao.getAllResponseByUsername(username)).thenReturn(null);

        sut.getReimbursementResponseList(username);
    }
    @Test
    public void test_getReimbursementList_valid(){
        String id = "0000";
        when(mockReimbursementDao.getAllReimbursementsByAuthorId(id)).thenReturn(new HashMap<>());

        sut.getReimbursementList(id);
    }
    @Test(expected = InvalidRequestException.class)
    public void test_getReimbursementList_invalid(){
        String id = "0000";
        when(mockReimbursementDao.getAllReimbursementsByAuthorId(id)).thenReturn(null);

        sut.getReimbursementList(id);
    }
    @Test
    public void test_getTypeId(){
        String type = "FOOD";
        when(mockReimbursementTypeDao.getTypeId(type)).thenReturn(new String());

        sut.getTypeId(type);
    }
    @Test
    public void test_getStatus(){
        String id = "00000";
        when(mockReimbursementStatusDao.getStatus(id)).thenReturn(new String());

        sut.getTypeId(id);
    }
    @Test
    public void test_submit(){
        ReimbursementRequest request = new ReimbursementRequest();
        request.setType("FOOD");
        String authorId = "506+50684068";
        when(mockReimbursementStatusDao.getStatusId("PENDING")).thenReturn("PENDING");
        when(mockReimbursementTypeDao.getTypeId(request.getType().toUpperCase())).thenReturn("FOOD");
    }


}