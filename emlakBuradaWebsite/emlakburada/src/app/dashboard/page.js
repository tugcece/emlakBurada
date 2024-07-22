import styles from "./page.module.css";
import Link from "next/link";
import ListingCard from "./components/listingCard";
import FilterButtons from "./components/filterButtons";
import Modal from "./components/modal";
import { fetchListings } from "../../../lib/api/listings";
import { NextIcon, PreviousIcon } from "../icons";
import withUser from "../hoc/withUser";

async function Listing({ filter, userId, page, size }) {
  const response = await fetchListings(filter, userId, page, size);
  return (
    <div className={styles.listing}>
      {response.data.content.map((listing, index) => (
        <ListingCard key={index} {...listing} />
      ))}
    </div>
  );
}

async function Dashboard({ searchParams, user }) {
  
  const filter = searchParams.filter || "All";
  const page = searchParams.page || 0;
  const size = searchParams.size || 5;
  const show = searchParams?.show;
  const userId = user;

  const response = await fetchListings(filter, userId, page, size);
  const  totalPages  = response.data.totalPages;
  const currentPage = response.data.currentPage;
  return (
    <main className={styles.main}>
      <div className={styles.dashboardContainer}>
        <div className={styles.filterContainer}>
          <FilterButtons initialFilter={filter} />
          <Link href="/dashboard/?show=true">
            <button className={styles.addListing}>
              <svg className={styles.svgIcon} viewBox="0 0 448 512">
                <path d="M432 256c0 17.7-14.3 32-32 32H288v112c0 17.7-14.3 32-32 32s-32-14.3-32-32V288H112c-17.7 0-32-14.3-32-32s14.3-32 32-32h112V112c0-17.7 14.3-32 32-32s32 14.3 32 32v112h112c17.7 0 32 14.3 32 32z" />
              </svg>
            </button>
          </Link>
        </div>
        <Listing filter={filter} userId={userId} page={page} size={size} />
        <div className={styles.pagination}>
          <Link
            href={`/dashboard?page=${
              currentPage > 0 ? currentPage - 1 : 0
            }&size=${size}`}
            passHref
          >
            <button
              className={`${styles.paginationButton} ${styles.previous}`}
              disabled={currentPage <= 0}
            >
              <PreviousIcon />
            </button>
          </Link>
          <Link
            href={`/dashboard?page=${
              currentPage < totalPages - 1 ? currentPage + 1 : currentPage
            }&size=${size}`}
            passHref
          >
            <button
              className={`${styles.paginationButton} ${styles.next}`}
              disabled={currentPage >= totalPages - 1}
            >
              <NextIcon />
            </button>
          </Link>
        </div>
      </div>

      {show && <Modal userId={userId} />}
    </main>
  );
}

export default withUser(Dashboard);
