import { getServerSession } from "next-auth/next";
import { authOptions } from "../api/auth/[...nextauth]/route";

const withUser = (Component) => {
  return async (props) => {
    const session = await getServerSession(authOptions);
    console.log(session?.user?.id);
    return <Component {...props} user={session?.user?.id || null} />;
  };
};

export default withUser;
