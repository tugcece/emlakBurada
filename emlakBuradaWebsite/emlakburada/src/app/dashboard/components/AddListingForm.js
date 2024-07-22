"use client";
import React, { useState } from "react";
import { useRouter } from "next/navigation";
import styles from "../edit/[listingId]/page.module.css";
import { createListing, uploadImage } from "../../../../lib/api/listings";
import toast from "react-hot-toast";
import * as Yup from "yup";

const AddListingForm = ({ onClose, userId }) => {
  const router = useRouter();
  const [formData, setFormData] = useState({
    title: "",
    address: "",
    roomNumber: "",
    size: "",
    floor: "",
    age: "",
    summary: "",
    deposit: "",
    price: "",
    details: "",
  });
  const [file, setFile] = useState(null);
  const [errors, setErrors] = useState({});

  const validationSchema = Yup.object().shape({
    title: Yup.string().required("Title is required"),
    address: Yup.string().required("Address is required"),
    roomNumber: Yup.number()
      .required("Number of rooms is required"),
    size: Yup.number().required("Size is required"),
    floor: Yup.number().required("Floor is required"),
    age: Yup.number().required("Age is required"),
    summary: Yup.string().required("Summary is required"),
    deposit: Yup.number().required("Deposit is required"),
    price: Yup.number().required("Price is required"),
    details: Yup.string().required("Description is required"),
  });

  const validate = async () => {
    try {
      await validationSchema.validate(formData, { abortEarly: false });
      return true;
    } catch (err) {
      const validationErrors = {};
      err.inner.forEach((error) => {
        validationErrors[error.path] = error.message;
      });
      setErrors(validationErrors);
      return false;
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const isValid = await validate();
    if (!isValid) {
      return;
    }
    try {
      console.log("Form Data saved:", userId);
      const listingId = await createListing(formData, userId);
      console.log("Listing created with ID:", listingId);

      if (file) {
        const formData = new FormData();
        formData.append("image", file);
        await uploadImage(listingId, formData);
        console.log("Image uploaded successfully!");
      }

      toast.success("Listing created and image uploaded successfully!");
      if (onClose) onClose();
      router.push("/dashboard");
    } catch (error) {
      console.error("Error saving data:", error);
      toast.error("Failed to create listing and upload image.");
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
          value={formData.title}
          onChange={handleChange}
        />
        {errors.title && (
          <div className={styles.inputHelper}>{errors.title}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="address">Address</label>
        <input
          type="text"
          id="address"
          name="address"
          value={formData.address}
          onChange={handleChange}
        />
        {errors.address && (
          <div className={styles.inputHelper}>{errors.address}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="roomNumber">Rooms</label>
        <input
          type="number"
          id="roomNumber"
          name="roomNumber"
          value={formData.roomNumber}
          onChange={handleChange}
        />
        {errors.roomNumber && (
          <div className={styles.inputHelper}>{errors.roomNumber}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="size">Size</label>
        <input
          type="number"
          id="size"
          name="size"
          value={formData.size}
          onChange={handleChange}
        />
        {errors.size && <div className={styles.inputHelper}>{errors.size}</div>}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="floor">Floor</label>
        <input
          type="number"
          id="floor"
          name="floor"
          value={formData.floor}
          onChange={handleChange}
        />
        {errors.floor && (
          <div className={styles.inputHelper}>{errors.floor}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="age">Age</label>
        <input
          type="number"
          id="age"
          name="age"
          value={formData.age}
          onChange={handleChange}
        />
        {errors.age && <div className={styles.inputHelper}>{errors.age}</div>}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="deposit">Deposit</label>
        <input
          type="number"
          id="deposit"
          name="deposit"
          value={formData.deposit}
          onChange={handleChange}
        />
        {errors.deposit && (
          <div className={styles.inputHelper}>{errors.deposit}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.quarterWidth}`}>
        <label htmlFor="price">Price</label>
        <input
          type="number"
          id="price"
          name="price"
          value={formData.price}
          onChange={handleChange}
        />
        {errors.price && (
          <div className={styles.inputHelper}>{errors.price}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="details">Description</label>
        <textarea
          id="details"
          name="details"
          cols="50"
          rows="10"
          value={formData.details}
          onChange={handleChange}
        />
        {errors.description && (
          <div className={styles.inputHelper}>{errors.description}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.halfWidth}`}>
        <label htmlFor="summary">Summary</label>
        <textarea
          id="summary"
          name="summary"
          cols="50"
          rows="10"
          value={formData.summary}
          onChange={handleChange}
        />
        {errors.summary && (
          <div className={styles.inputHelper}>{errors.summary}</div>
        )}
      </div>
      <div className={`${styles.inputBox} ${styles.fullWidth}`}>
        <button className={styles.fileButton}>
          Upload
          <input className={styles.file} name="file" type="file" id="file" onChange={handleFileChange}
          required/>
        </button>
      </div>
      <button className={styles.submit} type="submit" name="submit">
        Save
      </button>
    </form>
  );
};

export default AddListingForm;
