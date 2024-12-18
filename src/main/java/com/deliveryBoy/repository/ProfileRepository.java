package com.deliveryBoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliveryBoy.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity ,Long> {

}