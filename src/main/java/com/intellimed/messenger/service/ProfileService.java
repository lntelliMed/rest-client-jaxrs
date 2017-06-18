package com.intellimed.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.intellimed.messenger.database.DatabaseClass;
import com.intellimed.messenger.model.Profile;

public class ProfileService {
	
	private static Map<String, Profile> profiles = DatabaseClass.getProfiles();
			
	public ProfileService(){
		profiles.put("user1", new Profile(1L, "user1", "First Name", "Last Name", new Date()));
	}
			
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName){
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if (profile.getProfileName().isEmpty()){
			return null;
		}				
		profiles.put(profile.getProfileName(), profile);
		return profile;		
	}

	public Profile deleteProfile(String profileName){
		return profiles.remove(profileName);
	}
}
