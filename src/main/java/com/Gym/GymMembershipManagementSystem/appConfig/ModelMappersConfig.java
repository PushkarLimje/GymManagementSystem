package com.Gym.GymMembershipManagementSystem.appConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // defines beans
public class ModelMappersConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
