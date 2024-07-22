"use client"
import Link from "next/link";
import styles from "../page.module.css"; 
import Image from "next/image";
import logo from "../../../public/images/logo.png";
import { DashboardIcon, LogoutIcon, UserIcon } from "../icons";
import LogoutButton from "./logoutButton";

const Navbar = ({userId}) => {

  return (
    <>
      <div className={styles.navBar}>
        <div className={styles.logoBar}>
          <Image
            className={styles.logo}
            src={logo}
            width={50}
            height={50}
            alt="emlakBurada"
          />
          <Link href="/dashboard">
            <p className={styles.brand}>emlakburada</p>
          </Link>
        </div>
        <div className={styles.navItems}>
          <Link className={styles.navItem} href={`/user/${userId}`}>
            <UserIcon />
          </Link>
          <Link className={styles.navItem} href="/dashboard">
            <DashboardIcon />
          </Link>
          <LogoutButton/>
           
        </div>
      </div>
    </>
  );
};

export default Navbar;
