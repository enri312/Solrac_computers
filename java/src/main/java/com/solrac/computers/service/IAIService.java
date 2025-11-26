package com.solrac.computers.service;

import com.solrac.computers.model.DeviceType;

/**
 * Service interface for AI-powered repair diagnosis
 * Uses local Llama 3.2 3B Instruct model via GPT4All
 */
public interface IAIService {
    /**
     * Get repair diagnosis for a device problem
     * @param deviceType Type of device
     * @param problemDescription Description of the problem
     * @return AI-generated diagnosis and recommendations
     */
    String getDiagnosis(DeviceType deviceType, String problemDescription);
}
