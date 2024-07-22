import styles from "./page.module.css";
import Image from "next/image";
import logo from "../../public/images/loading.gif";

export default function loading() {
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
