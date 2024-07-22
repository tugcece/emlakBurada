"use client";
import React, { useState } from "react";
import styles from "../page.module.css";
import { fetchPurchase } from "../../../../../lib/api/purchases";
import { toast } from "react-hot-toast";

const PackageDetails = ({params}) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handlePurchase = async () => {
    setLoading(true);
    setError(null);
    const purchaseData = {
      packageId: 1,
      userId: params.userId,
      totalPrice: 100,
    };

    try {
      await fetchPurchase(purchaseData);
      setLoading(false);
      toast.success("Purchased successfully.");
    } catch (error) {
      setLoading(false);
      toast.error("Purchased failed,please try again.");
    }
  };

  return (
    <div className={styles.mainContent}>
      <div className={styles.header}>
        <p>Our Packages</p>
      </div>
      <div className={styles.package}>
        <div className={styles.packageCards}>
          <div className={styles.packageDetail}>
            <span className={styles.pricing}>
              <span>
                $49 <small>/ m</small>
              </span>
            </span>
            <p className={styles.packageName}>Basic Package</p>
            <p className={styles.packageDetailedInfo}>
              This plan will provide you with the right to publish 10 listings.
            </p>
            <ul className={styles.packageFeatures}>
              <li>
                <span className={styles.icon}>
                  <svg
                    height="24"
                    width="24"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path
                      fill="currentColor"
                      d="M10 15.172l9.192-9.193 1.415 1.414L10 18l-6.364-6.364 1.414-1.414z"
                    ></path>
                  </svg>
                </span>
                <span>
                  <strong>30</strong> days valid
                </span>
              </li>

              <li>
                <span className={styles.icon}>
                  <svg
                    height="24"
                    width="24"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path
                      fill="currentColor"
                      d="M10 15.172l9.192-9.193 1.415 1.414L10 18l-6.364-6.364 1.414-1.414z"
                    ></path>
                  </svg>
                </span>
                <span>multiple packages at the same time.</span>
              </li>
            </ul>
            <div className={styles.action}>
              <button
                className={styles.purchase}
                onClick={handlePurchase}
                disabled={loading}
              >
                {loading ? "Processing..." : "Purchase"}
              </button>
              {error && <p className={styles.error}>{error}</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PackageDetails;
