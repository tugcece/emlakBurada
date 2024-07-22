"use client"
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getProfile } from "./api/auth";
import Loading from "@/app/loading";

const withAuth = (WrappedComponent) => {
  return (props) => {
    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState(null);
    const router = useRouter();
    useEffect(() => {
      const checkAuth = async () => {
        const token = localStorage.getItem("token");
        const userId = localStorage.getItem("id");
      
        if (!token || !userId) {
          localStorage.clear();
          router.push("/login");
          return;
        }

        try {
          const profile = await getProfile(token,userId);
          setUser({ ...profile, userId });
        } catch (error) {
          console.log("error");
          router.push("/login");
        } finally {
          setLoading(false);
        }
      };

      checkAuth();
    }, []);

    if (loading) {
      return <Loading />;
    }

    return <WrappedComponent {...props} user={user} />;
  };
};

export default withAuth;
