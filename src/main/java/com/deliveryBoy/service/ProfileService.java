package com.deliveryBoy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.ProfileEntity;


public interface ProfileService {

	void saveProfile(ProfileEntity profileEntity);

	ProfileEntity getProfileById(Long userId);

	List<ProfileEntity> getAllProfiles();

	void deleteProfile(Long userId);

	

}
