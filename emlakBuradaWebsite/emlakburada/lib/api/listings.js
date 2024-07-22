import axios from "axios";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const fetchListings = async (filter, userId, page, size) => {
  try {
    console.log("Fetching", filter, userId);
    const filterName = filter === "Active" ? "ACTIVE" : "PASSIVE";
    console.log("Fetching", filterName);
    // const endpoint = filter === "All" ? "/all" : "/status";
    // console.log("Fetching", endpoint);
    const data =
      filter === "All"
        ? {
            page: page,
            size: size,
            userId: userId,
          }
        : {
            page: page,
            size: size,
            userId: userId,
            listingStatus: filterName,
          };

    const response = await axios.get(`${API_URL}listing/status`, {
      data: data,
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching listings:", error);
    throw error;
  }
};

export const createListing = async (listing, userId) => {
  try {
    console.log("Creating", listing);
    console.log("u", userId);
    const response = await axios.post(`${API_URL}listing`, listing, {
      headers: {
        userId: userId,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error creating listing:", error);
    throw error;
  }
};

export const uploadImage = async (listingId, formData) => {
  try {
    const response = await axios.post(
      `${API_URL}listing/image/${listingId}`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error uploading image:", error);
    throw error;
  }
};

export const getImage = async (listingId) => {
  try {
    console.log("Getting image",listingId);
    const response = await axios.get(`${API_URL}listing/image/${listingId}`, {
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

export const fetchListingDetails = async (listingId) => {
  try {
    const response = await axios.get(`${API_URL}listing/id/${listingId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching listings:", error);
    throw error;
  }
};

export const updateListing = async (listingId, listing) => {
  try {
    const response = await axios.put(
      `${API_URL}listing/update/${listingId}`,
      listing
    );
    return response.data;
  } catch (error) {
    console.error("Error updating listing:", error);
    throw error;
  }
};

export const deleteListing = async (listingId) => {
  try {
    const response = await axios.delete(
      `${API_URL}listing/delete/${listingId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error updating listing:", error);
    throw error;
  }
};
