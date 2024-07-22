import { config } from "@fortawesome/fontawesome-svg-core";
import "@fortawesome/fontawesome-svg-core/styles.css";
import { Inter } from "next/font/google";
import "../globals.css";
import "../../../lib/fontawesome";
import { getServerSession } from "next-auth/next";
import { authOptions } from "../api/auth/[...nextauth]/route";
import Navbar from "../components/navbar";
import { Toaster } from "react-hot-toast";

const inter = Inter({ subsets: ["latin"] });
config.autoAddCss = false;

export default async function dashboardLayout({ children }) {
  const session = await getServerSession(authOptions);
  const userId = session?.user?.id;
  return (
    <main>
      <Toaster position="top-right" />
      {userId ? <Navbar userId={userId} /> : null}
      {children}
    </main>
  ); 
}
