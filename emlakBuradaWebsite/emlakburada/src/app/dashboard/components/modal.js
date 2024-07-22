"use client"
import Link from "next/link";
import styles from "../page.module.css";
import AddListingForm from "./AddListingForm";
import { CloseIcon } from "../../icons";

function Modal({ onClose, userId }) {
  const handleModalClose = () => {
    if (onClose) onClose();
  };

  return (
    <div className={styles.modal}>
      <div className={styles.modalContent}>
        <div className={styles.close}>
          <Link
            href="/dashboard"
            className={styles.closeButton}
            onClick={handleModalClose}
          >
            <CloseIcon />
          </Link>
        </div>
        <div className={styles.container}>
          <div className={styles.formContainer}>
            <AddListingForm onClose={handleModalClose} userId={userId} />
          </div>
        </div>
      </div>
    </div>
  );
}
export default Modal;
