package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.AddGroupMemberDto;
import at.technikum.springrestbackend.dtos.GroupDto;
import at.technikum.springrestbackend.dtos.UserGroupViewDto;
import at.technikum.springrestbackend.repositories.GroupRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;

    public GroupController(GroupService groupService, GroupRepository groupRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public List<GroupDto> getAllGroups() {

        return groupService.getAllGroups();
    }

    @GetMapping("/my-groups")
    public List<GroupDto> getMyGroups(@AuthenticationPrincipal UserPrincipal principal) {
        return groupService.getGroupsForUser(principal.getId());
    }

    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable UUID id) {

        return groupService.getGroupById(id);
    }

    @PostMapping("/{id}/members")
    public GroupDto addMemberToGroup(@PathVariable("id") UUID groupId,
                                     @RequestBody @NotNull AddGroupMemberDto request) {
        return groupService.addMember(groupId, request.getUserId());
    }

    @PostMapping("/{id}/join")
    public GroupDto joinGroup(@PathVariable("id") UUID groupId,
                              @AuthenticationPrincipal UserPrincipal principal) {
        return groupService.addMember(groupId, principal.getId());
    }

    @GetMapping("/memberships")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserGroupViewDto> memberships(){
        return groupRepository.getUserGroupOverview();
    }

    @PostMapping
    public GroupDto createGroup(@AuthenticationPrincipal UserPrincipal principal,
                                @Valid @RequestBody GroupDto groupDto) {

        return groupService.createGroup(groupDto, principal.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.Group', 'update')")
    public GroupDto updateGroup(@PathVariable UUID id, @Valid @RequestBody GroupDto groupDto) {
        return groupService.updateGroup(id, groupDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.Group', 'delete')")
    public void deleteGroup(@PathVariable UUID id) {

        groupService.deleteGroup(id);
    }
}

