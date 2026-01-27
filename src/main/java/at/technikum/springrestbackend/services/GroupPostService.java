package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.GroupPostDto;
import at.technikum.springrestbackend.dtos.GroupPostResponseDto;
import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.entities.GroupPost;
import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.mappers.GroupPostMapper;
import at.technikum.springrestbackend.repositories.GroupPostRepository;
import at.technikum.springrestbackend.repositories.GroupRepository;
import at.technikum.springrestbackend.repositories.PostRepository;
import at.technikum.springrestbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class GroupPostService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupPostService.class);

    private final GroupPostRepository groupPostRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final GroupPostMapper groupPostMapper;
    private final UserRepository userRepository;

    public GroupPostService(GroupPostRepository groupPostRepository, GroupRepository groupRepository, PostRepository postRepository, GroupPostMapper groupPostMapper, UserRepository userRepository){
        this.groupPostRepository = groupPostRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.groupPostMapper = groupPostMapper;
        this.userRepository = userRepository;
    }

    public List<GroupPostResponseDto> getAllGroupPosts() {
        return groupPostRepository.findAll().stream().map(groupPostMapper::toResponseDto).toList();
    }

    public GroupPostResponseDto getGroupPostById(UUID id) {
        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(() -> {
            LOG.warn("GroupPost not found with id {}", id);
            return new ResourceNotFoundException("GroupPost not found with id" + id);
        });

        return groupPostMapper.toResponseDto(groupPost);
    }

    public UUID getGroupPostOwnerId(UUID postId){
        GroupPost post = groupPostRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found"));
        return post.getUser().getId();
    }

    public boolean isUserMemberOfGroup(UUID groupId, UUID userId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return group.getMembers().stream().anyMatch(user -> user.getId().equals(userId));
    }

    @Transactional
    public GroupPostResponseDto createGroupPost(GroupPostDto groupPostDto,
                                        UUID userId,
                                        UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            LOG.warn("Group not found with id {} when creating GroupPost", groupId);
            return new ResourceNotFoundException("Group not found with id: " + groupId);
        });

        // Verify that the user is a member of the group
        if (!isUserMemberOfGroup(groupId, userId)) {
            LOG.warn("User {} is not a member of group {} when attempting to create GroupPost", userId, groupId);
            throw new ResourceNotFoundException("User is not a member of this group");
        }

        Post post = postRepository.findById(groupPostDto.getPostId()).orElseThrow(() -> {
            LOG.warn("Post not found with id {} when creating GroupPost", groupPostDto.getPostId());
            return new ResourceNotFoundException("Post not found with id: " + groupPostDto.getPostId());
        });

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + userId));

        GroupPost groupPost = new GroupPost();
        groupPost.setUser(user);
        groupPost.setGroup(group);
        groupPost.setPost(post);

        GroupPost saved = groupPostRepository.save(groupPost);
        return groupPostMapper.toResponseDto(saved);
    }

    @Transactional
    public GroupPostResponseDto updateGroupPost(UUID id, GroupPostDto groupPostDto) {
        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(() -> {
            LOG.warn("GroupPost with id {} not found  when updating GroupPost {}", id);
            return new ResourceNotFoundException("GroupPost not found with id: " + id);
        });

        Group group = groupRepository.findById(groupPostDto.getGroupId()).orElseThrow(() -> {
            LOG.warn("Group not found with the id {} when updating GroupPost {}", groupPostDto.getGroupId(), id);
            return new ResourceNotFoundException("Group not found with id: " + groupPostDto.getGroupId());
        });

        Post post = postRepository.findById(groupPostDto.getPostId()).orElseThrow(() -> {
            LOG.warn("Post not found with id {} when updating GroupPost {}", groupPostDto.getPostId(), id);
            return new ResourceNotFoundException("Post not found with id: " + groupPostDto.getPostId());
        });

        groupPost.setGroup(group);
        groupPost.setPost(post);

        GroupPost updated = groupPostRepository.save(groupPost);
        return groupPostMapper.toResponseDto(updated);
    }

    @Transactional
    public void deleteGroupPost(UUID id) {
        if (groupPostRepository.existsById(id)) {
            groupPostRepository.deleteById(id);
            LOG.info("GroupPost with id {} was deleted.", id);
        } else {
            LOG.warn("Tried to delete GroupPost with id {}", id);
            throw new ResourceNotFoundException("GroupPost not found with id: " + id);
        }
    }
}

