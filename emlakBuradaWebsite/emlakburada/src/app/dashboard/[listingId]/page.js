import styles from "./page.module.css";
import Image from "next/image";
import Details from "./components/details";
import { fetchListingDetails } from "../../../../lib/api/listings";
import { getImage } from "../../../../lib/api/listings";


 async function ListingDetails({params}) {
  const {listingId} = params;
  console.log(listingId);
  const listings = await fetchListingDetails(listingId);
  const imageSrc = await getImage(listingId);
  return (
    <main className={styles.main}>
      <div className={styles.listingContainer}>
        <div className={styles.listingInfo}>
          <div className={styles.listingImage}>
            <Image src={imageSrc} fill={true} />
          </div>
          <div className={styles.listingInformation}>
            <div className={styles.baseInformation}>
              <h1 className={styles.listingName}>{listings.title}</h1>
              <div className={styles.listingPrice}> $ {listings.price}</div>
            </div>
            <div className={styles.detailedInformation}>
              <Details listings={listings} />
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
export default ListingDetails;