"use client";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import styles from "../page.module.css";
import { ThumbsDownIcon, ThumbsUpIcon } from "@/app/icons";

export default function FilterButtons({ initialFilter }) {
  const [selectedFilter, setSelectedFilter] = useState(initialFilter);
  const router = useRouter();

  useEffect(() => {
    setSelectedFilter(initialFilter);
  }, [initialFilter]);

  const handleFilterClick = (filter) => {
    router.push(`dashboard/?filter=${filter}`, { shallow: true });
  };

  return (
      <div className={styles.filters}>
        <button
          className={`${styles.filter} ${
            selectedFilter === "All" ? styles.active : ""
          }`}
          onClick={() => handleFilterClick("All")}
        >
          All
        </button>
        <button
          className={`${styles.filter} ${
            selectedFilter === "Active" ? styles.active : ""
          }`}
          onClick={() => handleFilterClick("Active")}
        >
          <ThumbsUpIcon/>
          active
        </button>
        <button
          className={`${styles.filter} ${
            selectedFilter === "Passive" ? styles.active : ""
          }`}
          onClick={() => handleFilterClick("Passive")}
        >
          <ThumbsDownIcon/>
          passive
        </button>
      </div>
   
  );
}
