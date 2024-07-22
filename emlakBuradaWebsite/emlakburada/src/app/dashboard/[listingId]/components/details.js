"use client";
import { useState } from "react";
import styles from "../page.module.css";

function Details({ listings }) {
  const [selectedDetail, setSelectedDetail] = useState("summary");

  const handleDetailChange = (detail) => {
    setSelectedDetail(detail);
  };

  return (
    <>
      <div className={styles.detailContainer}>
        {["Summary", "Details", "Address"].map((detail) => (
          <div
            key={detail}
            className={`${styles.details} ${
              selectedDetail === detail.toLowerCase() ? styles.active : ""
            }`}
            
            onClick={() => handleDetailChange(detail.toLowerCase())}
          >
            {detail}
          </div>
        ))}
      </div>
      <div className={styles.infoContainer}>
        <h3 className={styles.detailHeader}>
          {selectedDetail.charAt(0).toUpperCase() + selectedDetail.slice(1)}
        </h3>
        <div className={styles.detail}>
          <p>{listings[selectedDetail]}</p>
        </div>
      </div>
    </>
  );
}

export default Details;
