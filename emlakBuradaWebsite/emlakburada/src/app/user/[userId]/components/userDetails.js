import styles from "../page.module.css";
import Image from "next/image";
import { fetchUserById, getProfilePhoto } from "../../../../../lib/api/user";

async function UserDetails({params}) {
  const userId =params.userId;
  const response = await fetchUserById(userId);
    const imageSrc = await getProfilePhoto(userId);
  const userData = response.data;
  return (
    <div className={styles.userInfo}>
      <div className={styles.userContent}>
        <div className={styles.header}>
          <div className={styles.avatar}>
            <Image
              src={imageSrc}
              alt="avatar"
              fill={true}
              objectFit="cover"
              objectPosition="center"
              loading="lazy"
            />
          </div>
        </div>
        <div className={styles.user}>
          <div className={styles.userDetails}>
            <div className={styles.userDetail}>
              <span className={styles.names}>name</span>
              <span className={styles.infos}>{userData.name}</span>
            </div>
            <div className={styles.userDetail}>
              <span className={styles.names}>lastName</span>
              <span className={styles.infos}>{userData.lastName}</span>
            </div>
            <div className={styles.userDetail}>
              <span className={styles.names}>email</span>
              <span className={styles.infos}>{userData.email}</span>
            </div>
            <div className={styles.userDetail}>
              <span className={styles.names}>address</span>
              <span className={styles.infos}>{userData.address}</span>
            </div>
            <div className={styles.userDetail}>
              <span className={styles.names}>contact</span>
              <span className={styles.infos}>{userData.contact}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserDetails;
