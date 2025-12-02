package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.GroupPostDto;
import at.technikum.springrestbackend.entity.Group;
import at.technikum.springrestbackend.entity.GroupPost;
import at.technikum.springrestbackend.entity.Post;
import at.technikum.springrestbackend.repository.GroupPostRepository;
import at.technikum.springrestbackend.repository.GroupRepository;
import at.technikum.springrestbackend.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class GroupPostService {

    private static final Logger log = LoggerFactory.getLogger(GroupPostService.class);

    @Autowired
    private GroupPostRepository groupPostRepository;
    
    @Autowired
    private GroupRepository groupRepository;
    
    @Autowired
    private PostRepository postRepository;

    public List<GroupPost> getAllGroupPosts() {
        return groupPostRepository.findAll();
    }

    public GroupPost getGroupPostById(UUID id) {
        return groupPostRepository.findById(id).orElseThrow(() -> {
            log.warn("GroupPost not found with id {}", id);
            return new ResourceNotFoundException("GroupPost not found with id" + id);
        });
    }

    @Transactional
    public GroupPost createGroupPost(GroupPostDto groupPostDto) {
        Group group = groupRepository.findById(groupPostDto.getGroupId()).orElseThrow(() -> {
            log.warn("Group not found with id {} when creating GroupPost", groupPostDto.getGroupId());
            return new ResourceNotFoundException("Group not found with id: " + groupPostDto.getGroupId());
        });

        Post post = postRepository.findById(groupPostDto.getPostId()).orElseThrow(() -> {
            log.warn("Post not found with id {} when creating GroupPost", groupPostDto.getPostId());
            return new ResourceNotFoundException("Post not found with id: " + groupPostDto.getPostId());
        });

        GroupPost groupPost = new GroupPost();
        groupPost.setGroup(group);
        groupPost.setPost(post);

        return groupPostRepository.save(groupPost);
    }

    @Transactional
    public GroupPost updateGroupPost(UUID id, GroupPostDto groupPostDto) {
        GroupPost groupPost = getGroupPostById(id);

        Group group = groupRepository.findById(groupPostDto.getGroupId()).orElseThrow(() -> {
            log.warn("Group not found with id {} when updating GroupPost {}", groupPostDto.getGroupId(), id);
            return new ResourceNotFoundException("Group not found with id: " + groupPostDto.getGroupId());
        });

        Post post = postRepository.findById(groupPostDto.getPostId()).orElseThrow(() -> {
            log.warn("Post not found with id {} when updating GroupPost {}", groupPostDto.getPostId(), id);
            return new ResourceNotFoundException("Post not found with id: " + groupPostDto.getPostId());
        });

        groupPost.setGroup(group);
        groupPost.setPost(post);

        return groupPostRepository.save(groupPost);
    }

    @Transactional
    public void deleteGroupPost(UUID id) {
        if (groupPostRepository.existsById(id)) {
            groupPostRepository.deleteById(id);
            log.info("GroupPost with id {} was deleted.", id);
        } else {
            log.warn("Tried to delete GroupPost with id {}", id);
            throw new ResourceNotFoundException("GroupPost not found with id: " + id);
        }
    }
}

