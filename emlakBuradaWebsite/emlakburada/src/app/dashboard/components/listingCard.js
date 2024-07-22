"use client";
import styles from "../page.module.css";
import Link from "next/link";
import { DeleteIcon, EditIcon, GoIcon } from "@/app/icons";
import { deleteListing, getImage } from "../../../../lib/api/listings";
import { toast } from "react-hot-toast";
import { useState, useEffect } from "react";

const ListingCard = ({ id, title, price }) => {
  const [imageSrc, setImageSrc] = useState("");

  useEffect(() => {
    const fetchImage = async () => {
      try {
        const src = await getImage(id);
        setImageSrc(src);
      } catch (error) {
        console.error("Error fetching image:", error);
      }
    };
    fetchImage();
  }, [id]);

  const handleDelete = async () => {
    try {
      const response = await deleteListing(id);
      toast.success("Listing deleted successfully!");
      window.location.reload();
      return response.data;
    } catch (error) {
      console.error("Error updating listing:", error);
      toast.error("Failed to delete listing.");
      throw error;
    }
  };

  return (
    <div
      className={styles.card}
      style={{
        backgroundImage: `url(${imageSrc})`,
      }}
    >
      <div className={styles.transparent}></div>
      <div className={styles.listingContent}>
        <div className={styles.cardTop}>
          <span className={styles.name}>{title}</span>
          <div className={styles.icons}>
            <Link href={`/dashboard/edit/${id}`}>
              <EditIcon />
            </Link>
            <button className={styles.deleteButton} onClick={handleDelete}>
              <DeleteIcon />
            </button>
          </div>
        </div>
        <div className={styles.details}>
          <p className={styles.totalPrice}>{price} ₺</p>
          <Link href={`/dashboard/${id}`}>
            <button className={styles.button}>
              <GoIcon />
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ListingCard;
