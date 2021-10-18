package vbo.sharenurturebackend.snb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vbo.sharenurturebackend.snb.model.FbGroup;
import vbo.sharenurturebackend.snb.service.FbGroupService;

import java.util.List;

@RestController
public class FbGroupController {

    private FbGroupService fbGroupService;

    @Autowired
    public FbGroupController(FbGroupService fbGroupService) {
        this.fbGroupService = fbGroupService;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<FbGroup>> getAllGroups() {
        return ResponseEntity.ok(fbGroupService.getAllGroups());
    }

    @GetMapping(value = "/groups/{fbGroupId}", produces = "application/json")
    public ResponseEntity<FbGroup> getGroupById(@PathVariable String fbGroupId) {
        return ResponseEntity.ok(fbGroupService.getGroupById(fbGroupId));
    }

    @PostMapping("/groups")
    public ResponseEntity<FbGroup> addGroup(@RequestParam String fbGroupName, @RequestParam int fbGroupSize) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fbGroupService.addGroup(fbGroupName, fbGroupSize));
    }
}
