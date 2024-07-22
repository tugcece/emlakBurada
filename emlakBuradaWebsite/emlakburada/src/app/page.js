import styles from "./page.module.css";
import Image from "next/image";
import logo from "../../public/images/logo.png";

export default function Home() {

  return (
    <main className={styles.main}>
      <div className={styles.center}>
        <Image
          className={styles.logo}
          src={logo}
          width={70}
          height={70}
          alt="loading"
        />
      </div>
    </main>
  );
}
