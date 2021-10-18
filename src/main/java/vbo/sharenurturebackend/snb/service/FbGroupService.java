package vbo.sharenurturebackend.snb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vbo.sharenurturebackend.snb.exception.NoSuchFbGroupException;
import vbo.sharenurturebackend.snb.model.FbGroup;
import vbo.sharenurturebackend.snb.repository.FbGroupRepository;

import java.util.List;

@Service
public class FbGroupService {

    private FbGroupRepository fbGroupRepository;

    @Autowired
    public FbGroupService(FbGroupRepository fbGroupRepository) {
        this.fbGroupRepository = fbGroupRepository;
    }

    public List<FbGroup> getAllGroups() {
        return fbGroupRepository.findAll();
    }

    public FbGroup getGroupById(String fbGroupId) throws NoSuchFbGroupException {
        return fbGroupRepository.findById(fbGroupId).orElseThrow(() ->
                new NoSuchFbGroupException("There is no fbGroup with the id: " + fbGroupId));
    }

    public FbGroup addGroup(String fbGroupName, int fbGroupSize) throws NoSuchFbGroupException {
        return fbGroupRepository.save(new FbGroup(fbGroupName, fbGroupSize));
    }
}
