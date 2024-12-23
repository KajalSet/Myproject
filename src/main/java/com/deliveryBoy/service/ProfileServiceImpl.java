package com.deliveryBoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.entity.ProfileEntity;
import com.deliveryBoy.repository.NotificationRepository;
import com.deliveryBoy.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Override
	public void saveProfile(ProfileEntity profileEntity) {
		profileRepository.save(profileEntity);
		
	}

	@Override
	public ProfileEntity getProfileById(Long userId) {
		Optional<ProfileEntity> profile = profileRepository.findById(userId);
		
		return profile.orElse(null);
	}

	@Override
	public List<ProfileEntity> getAllProfiles() {
		
		return profileRepository.findAll();
	}

	@Override
	public void deleteProfile(Long userId) {

		profileRepository.deleteById(userId);
		
	}

	@Override
	public List<NotificationEntity> getNotificationForProfile(Long userId) {
		ProfileEntity profile = getProfileById(userId);
        return notificationRepository.findByProfileEntity(profile);
	}

	
	
}
