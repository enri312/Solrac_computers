import { DeviceType } from '../types';

/**
 * Calls the local LLaMA 3.2 3B Instruct model via HTTP.
 * The server is expected to be running at http://localhost:4891 and accept a POST request
 * with a JSON body { deviceType: string, problem: string }.
 * It should respond with JSON { diagnosis: string }.
 */
export const getRepairDiagnosis = async (
  deviceType: DeviceType,
  problem: string
): Promise<string> => {
  try {
    const response = await fetch('http://localhost:4891', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ deviceType, problem }),
    });

    if (!response.ok) {
      console.error('LLaMA API error:', response.status, response.statusText);
      return 'Error contacting AI service. Please try again later.';
    }

    const data = await response.json();
    // Expecting { diagnosis: string }
    if (data && typeof data.diagnosis === 'string') {
      return data.diagnosis;
    }
    console.warn('Unexpected LLaMA response format:', data);
    return 'No diagnosis generated.';
  } catch (error) {
    console.error('LLaMA API Error:', error);
    return 'Error connecting to AI service. Please try again later.';
  }
};