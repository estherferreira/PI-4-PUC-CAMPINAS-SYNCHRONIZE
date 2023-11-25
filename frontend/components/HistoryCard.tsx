import { Box, Text } from "@chakra-ui/react";
import DateCard from "./DateCard";
import { useRouter } from "next/router";

const HistoryCard = ({ day, month, symptoms }) => {
  const router = useRouter();
  return (
    <Box
      height="fit-content"
      backgroundColor="ice"
      borderRadius="16px"
      padding="22px"
      alignItems="center"
      justifyContent="space-between"
      display="flex"
    >
      <Box display="flex" alignItems="center" gap="20px">
        <DateCard month={month} day={day} current={true} />
        <Text fontFamily="poppins.400" fontSize="sm">
          {symptoms}
        </Text>
      </Box>
      <Text
        fontFamily="poppins.400"
        fontSize="sm"
        color="gray"
        cursor="pointer"
        onClick={() => {
          router.push("/diagnostic/:id");
        }}
      >
        Ver detalhes
      </Text>
    </Box>
  );
};

export default HistoryCard;
