"""
Simple script to exercise the endpoint. Will send random valid message with some invalid messages.
Uses standard python 2.7 library, no extra sugar needed.
"""
import argparse
import json
import logging
import random
import sys
import threading
import time
import urllib2

COUNTRIES = ["IE", "IT", "UK", "DE", "US", "FR", "ES", "PT", "RU"]
CURRENCIES = ["EUR", "USD", "GBP", "JPY", "SEK"]


def parse_args():
    p = argparse.ArgumentParser()
    p.add_argument("--endpoint", default="http://localhost:8080/message",
                   help="Endpoint to call. Default to http://localhost:8080/message")
    p.add_argument("--sleep", type=int, default=500,
                   help="Sleep time in ms between messages sent. Defaults to 500ms")
    p.add_argument("--threads", type=int, default=1,
                   help="Can be used to specify to start multiple threads")
    p.add_argument("--broken-chance", type=int, default=0,
                   help="""Chance to have a broken message (i.e. invalid). If provided, this parameter will create a broken
                   (i.e. unparseable) message approximately 1 in every X message where X is the value of this parameter.
                   Lower values means more frequent broken messages. Default 0, meaning disabled.""")
    return p.parse_args()


def make_message(args):
    headers = {
        "Content-Type": "application/json"
    }
    # check broken chance
    if args.broken_chance and random.randint(0, args.broken_chance) == 0:
        return ("message", headers)
    message = {
        "userId": "123456",
        "currencyFrom": random.choice(CURRENCIES),
        "currencyTo": random.choice(CURRENCIES),
        "amountSell": 800,
        "amountBuy": 1000,
        "rate": 0.8,
        "timePlaced": time.strftime("%d-%b-%y %H:%M:%S"),
        "originatingCountry": random.choice(COUNTRIES)
    }
    return message, headers


def post_messages(i, args):
    while True:
        message, headers = make_message(args)
        r = urllib2.Request(args.endpoint, json.dumps(message), headers=headers)
        try:
            f = urllib2.urlopen(r)
            logging.info("%d, %s", i, f.getcode())
        except urllib2.HTTPError as ex:
            logging.warn("%d, %s [%s]", i, ex.getcode(), ex.read())
        time.sleep(args.sleep / 1000.0)


def run(args):
    for i in range(args.threads):
        threading.Thread(target=post_messages, args=(i, args)).start()


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, stream=sys.stdout)
    args = parse_args()
    run(args)