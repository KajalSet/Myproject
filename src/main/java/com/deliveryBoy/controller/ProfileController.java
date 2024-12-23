package com.deliveryBoy.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.entity.ProfileEntity;
import com.deliveryBoy.response.ProfileWithNotificationResponse;
import com.deliveryBoy.response.SuccessResponse;
import com.deliveryBoy.service.ProfileService;

@Controller
@RequestMapping(path = "/api/profile")
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@PostMapping("/create-profile")
	public ResponseEntity<SuccessResponse> createProfile(@RequestParam("userId") Long userId,

			@RequestParam("name") String name,

			@RequestParam("phoneNumber") String phoneNumber,

			@RequestParam(value = "profilephoto", required = false) MultipartFile profilephoto

	) {

		try {
			ProfileEntity profileEntity = ProfileEntity.builder().userid(userId).name(name).phoneNumber(phoneNumber)
					.build();

			if (profilephoto != null) {
				profileEntity.setProfilePhoto(profilephoto.getBytes());
			}

			profileService.saveProfile(profileEntity);
			return ResponseEntity.ok(SuccessResponse.builder().message("Profile Created successfully!")
					.status(HttpStatus.CREATED).timeStamp(LocalDateTime.now()).build());
		} catch (IOException e) {
			return ResponseEntity.ok(SuccessResponse.builder().message("Data could't saved properly!")
					.status(HttpStatus.INTERNAL_SERVER_ERROR).timeStamp(LocalDateTime.now()).build());
		}

	}// create profile

	@GetMapping("/profile/{userId}")
	public ResponseEntity<ProfileEntity> getProfile(@PathVariable Long userId) {

		ProfileEntity profileEntity = profileService.getProfileById(userId);

		if (profileEntity == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(profileEntity);
	}// get profleby id

	@PutMapping("/profile/{userId}/photo")
	public ResponseEntity<SuccessResponse> updateProfilePhoto(@PathVariable Long userId,
			@RequestParam("profilePhoto") MultipartFile profilePhoto) {

		try {
			ProfileEntity profileById = profileService.getProfileById(userId);
			if (profileById == null) {

				return ResponseEntity.ok(SuccessResponse.builder().message("Profile not found.")
						.status(HttpStatus.NOT_FOUND).timeStamp(LocalDateTime.now()).build());

			}

			profileById.setProfilePhoto(profilePhoto.getBytes());
			profileService.saveProfile(profileById);
			return ResponseEntity.ok(SuccessResponse.builder().message("Profile photo updated successfully.")
					.status(HttpStatus.OK).timeStamp(LocalDateTime.now()).build());

		} catch (IOException e) {
			return ResponseEntity.ok(SuccessResponse.builder().message("update couldn't successfull.")
					.status(HttpStatus.INTERNAL_SERVER_ERROR).timeStamp(LocalDateTime.now()).build());

		}
	}// update profile by photo

	@PutMapping("/profile/{userId}")
	public ResponseEntity<SuccessResponse> updateProfile(@PathVariable Long userId,
			@RequestBody ProfileEntity profileEntity) {
		ProfileEntity existingProfile = profileService.getProfileById(userId);
		if (existingProfile == null) {
			return ResponseEntity.ok(SuccessResponse.builder().message("Profile not found!")
					.status(HttpStatus.NOT_FOUND).timeStamp(LocalDateTime.now()).build());

		}

		existingProfile.setName(profileEntity.getName());
		existingProfile.setPhoneNumber(profileEntity.getPhoneNumber());
		existingProfile.setProfilePhoto(profileEntity.getProfilePhoto());

		profileService.saveProfile(existingProfile);

		return ResponseEntity.ok(SuccessResponse.builder().message("Profile Updated Successfully").build());

	}// update profile

	@GetMapping("/allProfiles")
	public ResponseEntity<List<ProfileEntity>> getAllProfiles() {
		List<ProfileEntity> profiles = profileService.getAllProfiles();

		return ResponseEntity.ok(profiles);

	}// fetch all profiles

	@DeleteMapping("/deleteprofile/{userId}")
	public ResponseEntity<SuccessResponse> deleteProfile(@PathVariable Long userId) {
		ProfileEntity existingProfile = profileService.getProfileById(userId);

		if (existingProfile == null) {
			return ResponseEntity.ok(SuccessResponse.builder().message("Profile not found!")
					.status(HttpStatus.NOT_FOUND).timeStamp(LocalDateTime.now()).build());

		}

		profileService.deleteProfile(userId);
		return ResponseEntity.ok(SuccessResponse.builder().message("Profile deleted Successfully").build());
	}

	@GetMapping("/{userId}/notifications")
	public ResponseEntity<ProfileWithNotificationResponse>getNotification(@PathVariable Long userId){
		
		ProfileEntity profile = profileService.getProfileById(userId);
		
		List<NotificationEntity> notifications=profileService.getNotificationForProfile(userId);
		
		ProfileWithNotificationResponse response = new ProfileWithNotificationResponse(
		
		profile.getUserid(),
        profile.getName(),
        profile.getPhoneNumber(),
        profile.getProfilePhoto(),
        notifications);
    
        return ResponseEntity.ok(response);
        
	}//

	
	// logout

	// mpin management

}
