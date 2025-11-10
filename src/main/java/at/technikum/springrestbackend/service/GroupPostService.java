package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.GroupPostDto;
import at.technikum.springrestbackend.entity.Group;
import at.technikum.springrestbackend.entity.GroupPost;
import at.technikum.springrestbackend.entity.Post;
import at.technikum.springrestbackend.repository.GroupPostRepository;
import at.technikum.springrestbackend.repository.GroupRepository;
import at.technikum.springrestbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GroupPostService {

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
        GroupPost groupPost = groupPostRepository.findById(id).orElse(null);
        if (groupPost == null) {
            System.out.println("Error: GroupPost not found with id: " + id);
            return null;
        }
        return groupPost;
    }

    @Transactional
    public GroupPost createGroupPost(GroupPostDto groupPostDto) {
        Group group = groupRepository.findById(groupPostDto.getGroupId()).orElse(null);
        if (group == null) {
            System.out.println("Error: Group not found with id: " + groupPostDto.getGroupId());
            return null;
        }

        Post post = postRepository.findById(groupPostDto.getPostId()).orElse(null);
        if (post == null) {
            System.out.println("Error: Post not found with id: " + groupPostDto.getPostId());
            return null;
        }

        GroupPost groupPost = new GroupPost();
        groupPost.setGroup(group);
        groupPost.setPost(post);

        return groupPostRepository.save(groupPost);
    }

    @Transactional
    public GroupPost updateGroupPost(UUID id, GroupPostDto groupPostDto) {
        GroupPost groupPost = groupPostRepository.findById(id).orElse(null);
        if (groupPost == null) {
            System.out.println("Error: GroupPost not found with id: " + id);
            return null;
        }

        Group group = groupRepository.findById(groupPostDto.getGroupId()).orElse(null);
        if (group == null) {
            System.out.println("Error: Group not found with id: " + groupPostDto.getGroupId());
            return null;
        }

        Post post = postRepository.findById(groupPostDto.getPostId()).orElse(null);
        if (post == null) {
            System.out.println("Error: Post not found with id: " + groupPostDto.getPostId());
            return null;
        }

        groupPost.setGroup(group);
        groupPost.setPost(post);

        return groupPostRepository.save(groupPost);
    }

    @Transactional
    public void deleteGroupPost(UUID id) {
        if (groupPostRepository.existsById(id)) {
            groupPostRepository.deleteById(id);
        } else {
            System.out.println("Error: GroupPost not found with id: " + id);
        }
    }
}

