import styles from "./page.module.css";
import Image from "next/image";
import advertisingImage from "../../../../../public/images/addPage.gif";
import ListingForm from "./components/listingForm";

 function EditListing({ params }) {
  const { listingId } = params;
  return (
    <main className={styles.main}>
      <div className={styles.addListingContainer}>
        <div className={styles.addListing}>
          <div className={styles.advertising}>
            <div className={styles.advertisingImage}>
              <Image
                src={advertisingImage}
                fill={true}
                alt="advertising image"
              />
            </div>
            <div className={styles.slogan}>
              Turn Your Space into Income: List and Rent with Ease!
            </div>
          </div>
          <div className={styles.formContainer}>
            <ListingForm listingId={listingId} />
          </div>
        </div>
      </div>
    </main>
  );
}

export default EditListing;