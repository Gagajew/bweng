package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.GroupPostDto;
import at.technikum.springrestbackend.entity.GroupPost;
import at.technikum.springrestbackend.service.GroupPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groupposts")
public class GroupPostController {

    @Autowired
    private GroupPostService groupPostService;

    @GetMapping
    public List<GroupPost> getAllGroupPosts() {
        return groupPostService.getAllGroupPosts();
    }

    @GetMapping("/{id}")
    public GroupPost getGroupPostById(@PathVariable UUID id) {
        return groupPostService.getGroupPostById(id);
    }

    @PostMapping
    public GroupPost createGroupPost(@Valid @RequestBody GroupPostDto groupPostDto) {
        return groupPostService.createGroupPost(groupPostDto);
    }

    @PutMapping("/{id}")
    public GroupPost updateGroupPost(@PathVariable UUID id, @Valid @RequestBody GroupPostDto groupPostDto) {
        return groupPostService.updateGroupPost(id, groupPostDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroupPost(@PathVariable UUID id) {
        groupPostService.deleteGroupPost(id);
    }
}

