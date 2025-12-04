package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.GroupDto;
import at.technikum.springrestbackend.services.GroupService;
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
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable UUID id) {

        return groupService.getGroupById(id);
    }

    @PostMapping
    public GroupDto createGroup(@Valid @RequestBody GroupDto groupDto) {

        return groupService.createGroup(groupDto);
    }

    @PutMapping("/{id}")
    public GroupDto updateGroup(@PathVariable UUID id, @Valid @RequestBody GroupDto groupDto) {
        return groupService.updateGroup(id, groupDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable UUID id) {

        groupService.deleteGroup(id);
    }
}

