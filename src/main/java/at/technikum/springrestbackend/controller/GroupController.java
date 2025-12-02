package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.GroupDto;
import at.technikum.springrestbackend.entity.Group;
import at.technikum.springrestbackend.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable UUID id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Group createGroup(@Valid @RequestBody GroupDto groupDto) {
        return groupService.createGroup(groupDto);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable UUID id, @Valid @RequestBody GroupDto groupDto) {
        return groupService.updateGroup(id, groupDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
    }
}

