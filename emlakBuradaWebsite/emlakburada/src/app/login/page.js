import styles from "./page.module.css";
import Image from "next/image";
import logo from "../../../public/images/logo.png";
import LoginForm from "./components/loginForm";

export default function Login() {
  return (
    <main className={styles.main}>
      <div className={styles.left}>
        <div className={styles.navbar}>
          <Image
            className={styles.logo}
            src={logo}
            width={50}
            height={50}
            alt="emlakBurada"
          />
        </div>
        <div className={styles.information}>
          <div className={styles.textContainer}>
            <h1 className={styles.welcomeText}>Welcome!</h1>
            <h4 className={styles.infoText}>
              Create a free account or log in to get started using{" "}
              <span className={styles.brand}>emlakburada.</span>
            </h4>
          </div>
          <LoginForm />
        </div>
      </div>
      <div className={styles.right}></div>
    </main>
  );
}
