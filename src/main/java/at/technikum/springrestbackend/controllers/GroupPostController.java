package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.GroupPostDto;
import at.technikum.springrestbackend.dtos.GroupPostResponseDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.GroupPostService;
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

    public GroupPostController(GroupPostService groupPostService){
        this.groupPostService = groupPostService;
    }

    @GetMapping
    public List<GroupPostResponseDto> getAllGroupPosts() {
        return groupPostService.getAllGroupPosts();
    }

    @GetMapping("/{id}")
    public GroupPostResponseDto getGroupPostById(@PathVariable UUID id) {
        return groupPostService.getGroupPostById(id);
    }

    @PostMapping("/{groupId}/posts")
    public GroupPostResponseDto createGroupPost(@Valid @RequestBody GroupPostDto groupPostDto,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @PathVariable ("groupId") UUID groupId) {
        return groupPostService.createGroupPost(groupPostDto, userPrincipal.getId(), groupId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GroupPostResponseDto updateGroupPost(@PathVariable UUID id, @Valid @RequestBody GroupPostDto groupPostDto) {
        return groupPostService.updateGroupPost(id, groupPostDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGroupPost(@PathVariable UUID id) {
        groupPostService.deleteGroupPost(id);
    }
}

