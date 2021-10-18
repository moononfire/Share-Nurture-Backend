package vbo.sharenurturebackend.snb.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import vbo.sharenurturebackend.snb.exception.NoSuchFbGroupException;
import vbo.sharenurturebackend.snb.exception.RestResponseEntityExceptionHandler;
import vbo.sharenurturebackend.snb.model.FbGroup;
import vbo.sharenurturebackend.snb.service.FbGroupService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class FbGroupControllerTest {

    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Mock
    private FbGroupService fbGroupService;

    @InjectMocks
    private FbGroupController fbGroupController;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(fbGroupController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void getAllGroupsReturnsAllGroups() throws Exception {
        FbGroup group1 = new FbGroup("group1", 20);
        FbGroup group2 = new FbGroup("group2", 30);
        FbGroup group3 = new FbGroup("group3", 40);
        List<FbGroup> allGroups = new ArrayList<>();
        allGroups.add(group1);
        allGroups.add(group2);
        allGroups.add(group3);
        given(fbGroupService.getAllGroups()).willReturn(allGroups);

        MockHttpServletResponse response = mockMvc.perform(
                get("/groups").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("group1", "group2", "group3");
    }

    @Test
    void getAllGroupsEmptyReturnsEmptyList() throws Exception {
        given(fbGroupService.getAllGroups()).willReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/groups").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void getGroupByIdReturnsGroupWithId() throws Exception {
        String fbGroupId = "16";
        FbGroup group = new FbGroup(fbGroupId,"group1", 20);
        given(fbGroupService.getGroupById(fbGroupId)).willReturn(group);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/groups/" + fbGroupId).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(gson.toJson(group));
    }

    @Test
    void getGroupByIdDoesNotExistReturnsNotFound() throws Exception {
        String fbGroupId = "16";
        given(fbGroupService.getGroupById(fbGroupId)).willThrow(new NoSuchFbGroupException("There is no fbGroup with the id: " + fbGroupId));

        MockHttpServletResponse response = mockMvc.perform(
                        get("/groups/" + fbGroupId).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("Could not find the element:\nThere is no fbGroup with the id: " + fbGroupId);
    }

    @Test
    void addGroupReturnsCreated() throws Exception {
        String fbGroupId = "1";
        String fbGroupName = "group10";
        int groupSize = 20;
        FbGroup group = new FbGroup(fbGroupId, fbGroupName, groupSize);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("fbGroupName", fbGroupName);
        requestParams.add("fbGroupSize", Integer.toString(groupSize));
        given(fbGroupService.addGroup(fbGroupName, groupSize)).willReturn(group);

        MockHttpServletResponse response = mockMvc.perform(post("/groups/")
                        .params(requestParams)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(gson.toJson(group));
    }
}