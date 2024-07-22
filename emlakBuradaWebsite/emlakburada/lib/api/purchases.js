import axios from "axios";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const fetchPurchase = async (purchaseData) => {
  try {
    const response = await axios.post(
      `${API_URL}purchase`,
      purchaseData
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching purchase:", error);
    throw error;
  }
};
