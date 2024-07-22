import axios from "axios";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const fetchUserById = async (userId) => {
  try {
    console.log("Fetching user",userId);
    const response = await axios.get(`${API_URL}users/id/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user:", error);
    throw error;
  }
};
export const getProfilePhoto = async (userId) => {
  try {
    console.log("Getting image", userId);
    const response = await axios.get(`${API_URL}users/image/${userId}`, {
      responseType: "arraybuffer",
    });
    const base64 = Buffer.from(response.data, "binary").toString("base64");
    const image = `data:${response.headers["content-type"]};base64,${base64}`;
    return image;
  } catch (error) {
    console.error("Error uploading image:", error);
    throw error;
  }
};