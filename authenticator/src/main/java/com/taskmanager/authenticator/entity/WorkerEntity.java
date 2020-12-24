package com.taskmanager.authenticator.entity;

import com.taskmanager.common.Worker;
import com.taskmanager.common.WorkerRole;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"master", "slaves"})
@ToString(exclude = {"master", "slaves"})
public class WorkerEntity {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    private String name;
    @Column(nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master")
    private WorkerEntity master;
    @OneToMany(mappedBy = "master", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<WorkerEntity> slaves = new ArrayList<>();
    private WorkerRole role = WorkerRole.WORKER;

    public WorkerEntity(String username,
                        String name,
                        String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public User toUser() {
        return new User(username, password, List.of(new SimpleGrantedAuthority(role.name())));
    }

    public Worker toWorker() {
        String masterUsername = Optional.ofNullable(master)
                .map(WorkerEntity::getUsername)
                .orElse(null);

        List<String> slavesUsernames = slaves.stream()
                .map(WorkerEntity::getUsername)
                .collect(Collectors.toList());

        return new Worker(username, masterUsername, slavesUsernames);
    }
}
