"use client";
import { signOut } from "next-auth/react";
import styles from "../page.module.css";
import React from "react";
import { LogoutIcon } from "../icons";

const LogoutButton = () => {
  const handleLogout = () => {
    signOut({ callbackUrl: "/login" });
  };

  return (
    <button className={styles.navItem} onClick={handleLogout}>
      {" "}
      <LogoutIcon />
    </button>
  );
};

export default LogoutButton;
