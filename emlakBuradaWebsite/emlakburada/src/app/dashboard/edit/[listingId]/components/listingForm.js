"use client";
import React, { useState, useEffect } from "react";
import styles from "../page.module.css";
import {
  fetchListingDetails,
  updateListing,
} from "../../../../../../lib/api/listings";
import toast from "react-hot-toast";

const ListingForm = ({ listingId }) => {
  const [formData, setFormData] = useState({
    title: "",
    address: "",
    roomNumber: "",
    size: "",
    floor: "",
    age: "",
    deposit: "",
    price: "",
    details: "",
    summary:"",
    listingStatus: "", 
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchListingDetails(listingId);
        setFormData(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    if (listingId) {
      fetchData();
    }
  }, [listingId]);


  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    if (type === "checkbox") {
      setFormData({ ...formData, [name]: (checked ? "PASSIVE" : "ACTIVE") });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("formData:", formData);
      await updateListing(listingId, formData);
      console.log("Form Data updated:", formData);
      toast.success("Listing updated successfully!");
    } catch (error) {
      console.error("Error updating data:", error);
      toast.error("Failed to update listing.");
    }
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="title">Title</label>
        <input
          type="text"
          id="title"
          name="title"
          value={formData.title || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="address">Address</label>
        <input
          type="text"
          id="address"
          name="address"
          value={formData.address || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="roomNumber">Rooms</label>
        <input
          type="number"
          id="roomNumber"
          name="roomNumber"
          value={formData.roomNumber || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="size">Size</label>
        <input
          type="number"
          id="size"
          name="size"
          value={formData.size || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="floor">Floor</label>
        <input
          type="number"
          id="floor"
          name="floor"
          value={formData.floor || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="age">Age</label>
        <input
          type="number"
          id="age"
          name="age"
          value={formData.age || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="deposit">Deposit</label>
        <input
          type="number"
          id="deposit"
          name="deposit"
          value={formData.deposit || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="price">Price</label>
        <input
          type="number"
          id="price"
          name="price"
          value={formData.price || ""}
          onChange={handleChange}
        />
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="checkbox">Active</label>
        <label className={styles.active}>
          <input
            type="checkbox"
            id="listingStatus"
            name="listingStatus"
            value={formData.listingStatus}
            onChange={handleChange}
          />
          <div className={styles.check}></div>
        </label>
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="details">Description</label>
        <textarea
          type="text"
          id="details"
          name="details"
          cols="50"
          rows="10"
          value={formData.details}
          onChange={handleChange}
        ></textarea>
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="summary">Summary</label>
        <textarea
          type="text"
          id="summary"
          name="summary"
          cols="50"
          rows="10"
          value={formData.summary}
          onChange={handleChange}
        ></textarea>
      </div>
      <button className={styles.fileButton}>
        Upload
        <input className={styles.file} name="file" type="file" />
      </button>
      <button className={styles.submit} type="submit" name="submit">
        Save
      </button>
    </form>
  );
};

export default ListingForm;
