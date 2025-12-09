package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.GroupPostDto;
import at.technikum.springrestbackend.entities.GroupPost;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.GroupPostService;
import at.technikum.springrestbackend.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groupposts")
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final GroupService groupService;

    public GroupPostController(GroupPostService groupPostService, GroupService groupService){
        this.groupPostService = groupPostService;
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupPostDto> getAllGroupPosts() {
        return groupPostService.getAllGroupPosts();
    }

    @GetMapping("/{id}")
    public GroupPostDto getGroupPostById(@PathVariable UUID id) {
        return groupPostService.getGroupPostById(id);
    }

    @PostMapping
    public GroupPostDto createGroupPost(@Valid @RequestBody GroupPostDto groupPostDto) {
        return groupPostService.createGroupPost(groupPostDto);
    }

    @PutMapping("/{id}")
    public GroupPostDto updateGroupPost(@PathVariable UUID id, @Valid @RequestBody GroupPostDto groupPostDto) {
        return groupPostService.updateGroupPost(id, groupPostDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroupPost(@PathVariable UUID id) {
        groupPostService.deleteGroupPost(id);
    }
}

