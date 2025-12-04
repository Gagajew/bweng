package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.GroupPostDto;
import at.technikum.springrestbackend.services.GroupPostService;
import jakarta.validation.Valid;
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

