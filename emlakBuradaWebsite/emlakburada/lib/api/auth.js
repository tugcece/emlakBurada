import axios from "axios";
import Cookies from "next-cookies";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const login = async (credentials) => {
  try {
    const response = await axios.post(`${API_URL}auth/login`, credentials, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
    console.log(response);
    document.cookie = `token=${response.data.token}; path=/`;
    document.cookie = `id=${response.data.id}; path=/`;
    return response.data;
  } catch (error) {
    console.error("Error logging in:", error);
    throw error;
  }
};

