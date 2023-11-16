import {
  Box,
  Button,
  Checkbox,
  Flex,
  IconButton,
  Input,
  Radio,
  RadioGroup,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import FormInput from "../components/FormInput";
import { useState } from "react";
import { InfoIcon } from "@chakra-ui/icons";
import ProgressBar from "../components/ProgressBar";
import DateCard from "../components/DateCard";
import { FiLogOut } from "react-icons/fi";
import { useRouter } from "next/router";

const History = () => {
  const router = useRouter();
  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box display="flex" gap="12px" justifyContent="space-between">
          <Flex align="center" gap="15px">
            <Box
              height="56px"
              width="56px"
              borderRadius="full"
              backgroundColor="black"
            />
            <Box>
              <Text fontFamily="poppins.400" fontSize="lg">
                Sophia Iwara
              </Text>
              <Text
                fontFamily="poppins.400"
                color="gray"
                cursor="pointer"
                onClick={() => {
                  router.push("/profile");
                }}
              >
                Ver perfil
              </Text>
            </Box>
          </Flex>
          <Flex align="center" gap="10px">
            <Text fontFamily="poppins.400" cursor="pointer">
              Ir embora
            </Text>
            <FiLogOut />
          </Flex>
        </Box>
        <Flex justifyContent="space-between" marginTop="120px">
          <Text fontFamily="poppins.500" fontSize="2xl">
            Ultimos diagnósticos
          </Text>
          <Button
            fontFamily="inter.500"
            color="brand.900"
            backgroundColor="black"
            width="200px"
            marginBottom="115px"
            onClick={() => {
              router.push("/diagnostic");
            }}
          >
            Fazer diagnóstico
          </Button>
        </Flex>
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
            <DateCard month="out" day="22" current={true} />
            <Text fontFamily="poppins.400" fontSize="sm">
              Estou me sentindo muito quente e suando muito, minha cabeça está
              doendo muito, e minha garganta dói quando engulo
            </Text>
          </Box>
          <Text
            fontFamily="poppins.400"
            fontSize="sm"
            color="gray"
            cursor="pointer"
          >
            Ver detalhes
          </Text>
        </Box>
      </Box>
    </Box>
  );
};

export default History;
