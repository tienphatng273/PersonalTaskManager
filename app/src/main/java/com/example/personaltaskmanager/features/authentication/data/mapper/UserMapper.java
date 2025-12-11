package com.example.personaltaskmanager.features.authentication.data.mapper;

import com.example.personaltaskmanager.features.authentication.data.local.entity.UserEntity;
import com.example.personaltaskmanager.features.authentication.data.model.User;

/**
 * Mapper chuyển giữa:
 *  - UserEntity (Local Room)
 *  - User (Domain Model)
 *
 * Mục tiêu:
 *  - Tách biệt domain khỏi tầng database
 *  - Dễ mở rộng khi thêm Firebase DTO sau này
 */
public class UserMapper {

    public static User toModel(UserEntity entity) {
        if (entity == null) return null;

        // thêm id vào constructor
        return new User(
                entity.id,
                entity.username,
                entity.email,
                entity.password,
                entity.role
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity e = new UserEntity(
                user.username,
                user.email,
                user.password
        );

        // truyền id ngược vào Entity
        e.id = user.id;
        e.role = user.role;

        return e;
    }
}
